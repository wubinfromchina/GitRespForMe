package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wb.reggie.DTO.SetmealDto;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.Category;
import com.wb.reggie.pojo.Dish;
import com.wb.reggie.pojo.Setmeal;
import com.wb.reggie.pojo.SetmealDish;
import com.wb.reggie.service.CategoryService;
import com.wb.reggie.service.SetmealDishService;
import com.wb.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 套餐管理控制层
 * @Title: SetmealController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/31 17:13
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 套餐管理分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public RetObj<Page> page(int page,int pageSize,String name){
        log.info("准备进行分页中");
        //分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        //模糊查询
        wrapper.like(name!=null,Setmeal::getName,name);
        //价格降序,时间降序
        wrapper.orderByDesc(Setmeal::getPrice).orderByDesc(Setmeal::getUpdateTime);
        //分页查询
        setmealService.page(pageInfo,wrapper);
        //对象拷贝,忽略records属性
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        //填充属性
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            //复制
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            //查询套餐分类名
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmealDto.setCategoryName(category.getName());

            return setmealDto;
        }).collect(Collectors.toList());
        //填充至setmealDtoPage的records属性
        setmealDtoPage.setRecords(list);

        return RetObj.success(setmealDtoPage);
    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public RetObj<String> save(@RequestBody SetmealDto setmealDto){
        log.info("准备保存套餐");

        setmealService.saveWithDish(setmealDto);

        return RetObj.success("添加成功");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping()
    @CacheEvict(value = "setmealCache",allEntries = true)
    public RetObj<String> delete(String ids){
        log.info("准备删除");
        //切割字符串获取id数组
        String[] setmealIds = ids.split(",");
        setmealService.deleteWithFlavor(setmealIds);

        return RetObj.success("删除成功");
    }

    /**
     * 修改（回显页面）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RetObj<SetmealDto> update(@PathVariable String id){
        log.info("回显页面");
        //拷贝数据
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();//准备返回前端的封装类
        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询关联的菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        wrapper.orderByDesc(SetmealDish::getPrice);
        List<SetmealDish> list = setmealDishService.list(wrapper);
        //查询套餐分类名
        String name = categoryService.getById(setmeal.getCategoryId()).getName();
        //补全参数
        setmealDto.setSetmealDishes(list);
        setmealDto.setCategoryName(name);

        return RetObj.success(setmealDto);
    }

    /**
     * 保存修改
     * @param setmealDto
     * @return
     */
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public RetObj<String> edit(@RequestBody SetmealDto setmealDto){
        log.info("准备保存修改");
        setmealService.editWithDish(setmealDto);

        return RetObj.success("修改成功");
    }


    /**
     * 起售
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public RetObj<String> startSale(String ids){
        log.info("准备起售");
        String[] setmealIds = ids.split(",");

        setmealService.startSaleByIds(setmealIds);

        return RetObj.success("修改状态成功");
    }

    /**
     * 停售
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public RetObj<String> endSale(String ids){
        log.info("准备停售");
        String[] setmealIds = ids.split(",");

        setmealService.endSaleByIds(setmealIds);

        return RetObj.success("修改状态成功");
    }

    /**
     * 返回套餐对应的菜品数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    public RetObj<List<SetmealDto>> list(Setmeal setmeal){
        //查询套餐分类下对应的套餐
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        wrapper.eq(Setmeal::getStatus,1);//起售状态
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmealList = setmealService.list(wrapper);
        //转换数据至SetmealDto
        List<SetmealDto>  list = setmealList.stream().map((item) -> {
            //拷贝
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            //填充属性
            LambdaQueryWrapper<SetmealDish> setmealDishWrapper = new LambdaQueryWrapper<>();
            setmealDishWrapper.eq(SetmealDish::getSetmealId,item.getId());
            setmealDishWrapper.orderByDesc(SetmealDish::getPrice);
            List<SetmealDish> setmealDishList = setmealDishService.list(setmealDishWrapper);
            setmealDto.setSetmealDishes(setmealDishList);
            String categoryName = categoryService.getById(item.getCategoryId()).getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());

        return RetObj.success(list);
    }
}
