package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.DTO.SetmealDto;
import com.wb.reggie.pojo.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    void saveWithDish(SetmealDto setmealDto);

    void endSaleByIds(String[] setmealIds);

    void startSaleByIds(String[] setmealIds);

    void deleteWithFlavor(String[] setmealIds);

    void editWithDish(SetmealDto setmealDto);
}
