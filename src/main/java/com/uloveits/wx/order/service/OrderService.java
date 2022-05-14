package com.uloveits.wx.order.service;

import com.baomidou.mybatisplus.service.IService;
import com.uloveits.wx.common.PageResult;
import com.uloveits.wx.order.model.Order;
import java.util.Map;

/**
 * @author lyrics
 */
public interface OrderService extends IService<Order> {

    String add(Order param);

    Boolean update(Order order);

    /**
     * 取消订单
     */
    Boolean cancel(Map<String, Object> param);

    /**
     * 去发货
     */
    Boolean send(Map<String, Object> param);

    Boolean confirm(Map<String, Object> param);

    Integer selectCountByStatus(String userId,Integer status);

    PageResult<Order> getPage(Map<String, Object> param);

    PageResult<Order> getList(Map<String, Object> param);

    Order detail(String orderId);

}
