package com.wb.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wb.reggie.pojo.Category;

public interface CategoryService extends IService<Category> {

    void remove(long id);
}
