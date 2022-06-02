package com.wb.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wb.reggie.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
