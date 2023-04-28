package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.OrderItem;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 14:26
 */
public interface OrderItemDao {
    /**
     * 添加订单项
     * @param orderItem
     */
    boolean addOrderItem(OrderItem orderItem);

    /**
     * 通过订单id查询订单项
     * @param orderId
     * @return
     */
    List<OrderItem> findOrderItemByOrderId(int orderId);
}
