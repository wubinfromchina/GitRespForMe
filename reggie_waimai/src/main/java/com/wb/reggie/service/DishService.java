package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.DTO.DishDto;
import com.wb.reggie.pojo.Dish;

public interface DishService extends IService<Dish> {

    void saveWithFlavor(DishDto dishDto);

    void editWithFlavor(DishDto dishDto);

    void deleteWithFlavor(String[] dishIds);

    void startSaleByIds(String[] dishIds);

    void endSaleByIds(String[] dishIds);
}
