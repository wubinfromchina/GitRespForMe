package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.DTO.SetmealDto;
import com.wb.reggie.mapper.SetmealMapper;
import com.wb.reggie.pojo.Setmeal;
import com.wb.reggie.pojo.SetmealDish;
import com.wb.reggie.service.SetmealDishService;
import com.wb.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 套餐业务逻辑层
 * @Title: SetmealServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 18:32
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 保存新建套餐
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐信息
        super.save(setmealDto);
        //循环遍历list填充属性
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        //保存套餐菜品信息
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 停售套餐
     * @param setmealIds
     */
    @Override
    @Transactional
    public void endSaleByIds(String[] setmealIds) {
        for (String setmealId : setmealIds) {
            Setmeal setmeal = super.getById(setmealId);
            setmeal.setStatus(0);
            super.updateById(setmeal);
        }
    }

    /**
     * 起售套餐
     * @param setmealIds
     */
    @Override
    @Transactional
    public void startSaleByIds(String[] setmealIds) {
        for (String setmealId : setmealIds) {
            Setmeal setmeal = super.getById(setmealId);
            setmeal.setStatus(1);
            super.updateById(setmeal);
        }
    }

    /**
     * 删除套餐
     * @param setmealIds
     */
    @Override
    @Transactional
    public void deleteWithFlavor(String[] setmealIds) {
        for (String setmealId : setmealIds) {
            //删除本次循环套餐下所有的菜品
            LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SetmealDish::getSetmealId,setmealId);
            setmealDishService.remove(wrapper);
            //删除该套餐
            super.removeById(setmealId);

        }
    }

    @Override
    @Transactional
    public void editWithDish(SetmealDto setmealDto) {
        //保存套餐内容的修改
        super.updateById(setmealDto);
        //删除关联的菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(wrapper);
        //遍历赋值后保存修改后关联的菜品
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : list) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(list);
    }
}
