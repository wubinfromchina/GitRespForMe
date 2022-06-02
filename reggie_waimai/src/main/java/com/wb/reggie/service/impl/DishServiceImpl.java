package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.DTO.DishDto;
import com.wb.reggie.mapper.DishMapper;
import com.wb.reggie.pojo.Dish;
import com.wb.reggie.pojo.DishFlavor;
import com.wb.reggie.service.DishFlavorService;
import com.wb.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 菜单业务逻辑层
 * @Title: DishServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 18:25
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 保存菜品和口味至对应表中
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品至dish表
        super.save(dishDto);
        //获取菜品id
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        //循环遍历dishDto中的list成员变量
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
        }
        //保存菜品口味至口味表
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 修改菜品及口味
     * @param dishDto
     */
    @Override
    @Transactional
    public void editWithFlavor(DishDto dishDto) {
        //修改菜品dish
        super.updateById(dishDto);
        //获取菜品id
        Long id = dishDto.getId();
        //接收菜品口味集合
        List<DishFlavor> flavors = dishDto.getFlavors();
        //删除关联的菜品口味
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,id);
        dishFlavorService.remove(wrapper);
        //循环遍历dishDto中的list成员变量
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(id);
        }
        //保存菜品口味至口味表
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 删除菜品及关联的口味
     * @param dishIds
     */
    @Override
    @Transactional
    public void deleteWithFlavor(String[] dishIds) {
        for (String dishId : dishIds) {//遍历取得每个被选中的菜品id
            Dish dish = super.getById(dishId);
            //删除关联的菜品口味
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId,dishId);
            dishFlavorService.remove(wrapper);
            //删除菜品
            super.removeById(dishId);
        }
    }

    /**
     * 起售（修改status状态）
     * @param dishIds
     */
    @Override
    @Transactional
    public void startSaleByIds(String[] dishIds) {
        for (String dishId : dishIds) {//循环遍历取得id
            Dish dish = super.getById(dishId);
            //修改状态
            dish.setStatus(1);
            super.updateById(dish);
        }
    }

    /**
     * 停售（修改status状态）
     * @param dishIds
     */
    @Override
    @Transactional
    public void endSaleByIds(String[] dishIds) {
        for (String dishId : dishIds) {//循环遍历取得id
            Dish dish = super.getById(dishId);
            //修改状态
            dish.setStatus(0);
            super.updateById(dish);
        }
    }
}
