package com.wb.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wb.reggie.common.RetObj;
import com.wb.reggie.pojo.Category;
import com.wb.reggie.service.CategoryService;
import com.wb.reggie.service.DishService;
import com.wb.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 分类管理控制层
 * @Title: CategoryController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 15:44
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public RetObj<String> addCategory(@RequestBody Category category){
        log.info("正常访问");

        categoryService.save(category);
        return RetObj.success("添加成功");
    }

    /**
     * 分页查询菜品管理
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public RetObj<Page> page(int page,int pageSize){
        log.info("准备进行分页查询");
        //分页构造器
        Page<Category> pageInfo = new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper();
        //查询条件,排序
        wrapper.orderByAsc(Category::getSort);
        //分页查询
        categoryService.page(pageInfo,wrapper);
        return RetObj.success(pageInfo);
    }

    /**
     * 删除记录
     * @param ids
     * @return
     */
    @DeleteMapping
    public RetObj<String> delete(Long ids){
        log.info("准备删除{}该条数据",ids);
        //删除该分类
        categoryService.remove(ids);
        return RetObj.success("删除成功");
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @PutMapping
    public RetObj<String> update(@RequestBody Category category){
        log.info("准备修改中");
        //修改人及修改时间在公共字段填充类中已进行处理
        categoryService.updateById(category);
        return RetObj.success("修改成功");
    }

    @GetMapping("/list")
    public RetObj<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType()!=null,Category::getType,category.getType());
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(wrapper);

        return RetObj.success(list);
    }

}
