package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.common.RuiJiException;
import com.wb.reggie.mapper.CategoryMapper;
import com.wb.reggie.pojo.Category;
import com.wb.reggie.pojo.Dish;
import com.wb.reggie.pojo.Setmeal;
import com.wb.reggie.service.CategoryService;
import com.wb.reggie.service.DishService;
import com.wb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 分类管理业务逻辑层
 * @Title: CategoryServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 15:47
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类
     * @param id
     */
    public void remove(long id){
        //查询是否关联了菜品或者套餐,有则抛出异常
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        //查询条件
        dishWrapper.eq(Dish::getCategoryId,id);
        setmealWrapper.eq(Setmeal::getCategoryId,id);

        int dishSize = dishService.count(dishWrapper);
        int setmealSize = setmealService.count(setmealWrapper);

        if (dishSize>0 || setmealSize>0){
            throw new RuiJiException("菜品分类下有关联的菜品或套餐");
        }

        //无结果则直接删除该菜品分类
        super.removeById(id);
    }
}
