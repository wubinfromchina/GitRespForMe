package com.wb.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.reggie.common.RuiJiException;
import com.wb.reggie.mapper.OrdersMapper;
import com.wb.reggie.pojo.*;
import com.wb.reggie.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description: 订单业务逻辑层
 * @Title: OrdersServiceImpl
 * @Package com.wb.reggie.service.impl
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/31 21:34
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    /**
     * 提交订单
     * @param orders
     */
    @Override
    @Transactional
    public void submit(Orders orders) {
        Long userId = orders.getUserId();
        //购物车菜品套餐明细
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        shoppingCartWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartWrapper);
        //若购物车为空，则抛出业务异常
        if (shoppingCartList.size()==0 || shoppingCartList == null) {
            throw new RuiJiException("购物车是空的，请添加一些喜欢的商品吧");
        }
        //MP工具类获取id
        long orderId = IdWorker.getId();
        //获取总价
        AtomicInteger account = new AtomicInteger(0);

        List<OrderDetail> list =  shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setImage(item.getImage());
            orderDetail.setName(item.getName());
            orderDetail.setAmount(item.getAmount());
            account.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        //用户详情
        User user = userService.getById(orders.getUserId());
        //地址明细
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new RuiJiException("您的地址好像有问题,请检查是否过期或者超出配送范围吧");
        }
        //填充属性
        orders.setId(orderId);
        orders.setNumber(String.valueOf(orderId));
        orders.setStatus(2);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(account.get()));//订单总金额
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        //保存订单
        super.save(orders);

        //存入订单菜品表

        orderDetailService.saveBatch(list);

        //清空购物车
        shoppingCartService.remove(shoppingCartWrapper);
    }
}
