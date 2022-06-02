package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wb.reggie.DTO.DishDto;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.Category;
import com.wb.reggie.pojo.Dish;
import com.wb.reggie.pojo.DishFlavor;
import com.wb.reggie.service.CategoryService;
import com.wb.reggie.service.DishFlavorService;
import com.wb.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 菜品控制层
 * @Title: DishController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 20:48
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public RetObj<Page> page(int page,int pageSize,String name){
        //分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        //模糊查询
        wrapper.like(name!=null,Dish::getName,name);
        //价格降序,时间降序
        wrapper.orderByDesc(Dish::getPrice).orderByDesc(Dish::getUpdateTime);
        //分页查询
        dishService.page(pageInfo,wrapper);
        //对象拷贝,忽略records属性
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //填充属性
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //菜品分类名称
            String categoryName = categoryService.getById(categoryId).getName();
            dishDto.setCategoryName(categoryName);

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return RetObj.success(dishDtoPage);
    }

    /**
     * 保存创建的菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public RetObj<String> save(@RequestBody DishDto dishDto){
        log.info("准备保存中");

        dishService.saveWithFlavor(dishDto);

        return RetObj.success("添加成功");
    }

    /**
     * 修改数据(回显页面)
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RetObj<DishDto> update(@PathVariable Long id){
        log.info("准备回显该条数据");
        //需回显数据
        //1.查询该条数据
        Dish dish = dishService.getById(id);
        //2.查询关联的数据(口味，菜品分类)
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(wrapper);

        Category category = categoryService.getById(dish.getCategoryId());
        //3.封装数据(拷贝)
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setFlavors(list);
        dishDto.setCategoryName(category.getName());

        return RetObj.success(dishDto);
    }

    /**
     * 修改数据（保存）
     * @param dishDto
     * @return
     */
    @PutMapping
    public RetObj<String> edit(@RequestBody DishDto dishDto){
        log.info("准备保存数据");
        dishService.editWithFlavor(dishDto);

        return RetObj.success("修改成功");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping()
    public RetObj<String> delete(String ids){
        log.info("准备删除");
        //切割字符串获取id数组
        String[] dishIds = ids.split(",");
        dishService.deleteWithFlavor(dishIds);

        return RetObj.success("删除成功");
    }

    /**
     * 起售
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public RetObj<String> startSale(String ids){
        log.info("准备起售");
        String[] dishIds = ids.split(",");
        dishService.startSaleByIds(dishIds);
        return RetObj.success("修改状态成功");
    }

    /**
     * 停售
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public RetObj<String> endSale(String ids){
        log.info("准备停售");
        String[] dishIds = ids.split(",");
        dishService.endSaleByIds(dishIds);
        return RetObj.success("修改状态成功");
    }

    /**
     * 查询分类对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public RetObj<List<DishDto>> list(Dish dish){
        log.info("准备返回菜品集合");
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        wrapper.eq(Dish::getStatus,1);//起售状态
        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(wrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            //拷贝
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            //条件构造器
            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorWrapper);
            dishDto.setFlavors(dishFlavorList);
            Category category = categoryService.getById(item.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        return RetObj.success(dishDtoList);
    }
}
