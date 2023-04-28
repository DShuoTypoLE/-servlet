package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Order;
import com.iweb.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 14:26
 */
public interface OrderDao {
    /**
     * 添加订单
     * @param order
     * @return
     */
    boolean addOrder(Order order);

    /**
     * 根据用户id查询订单数量
     * @param userId
     * @return
     */
    long orderReadCount(int userId);

    /**
     * 查询我的订单列表
     * @param pageBean
     * @param userId
     * @return
     */
    List<Order> myOrderList(PageBean pageBean, int userId);

    /**
     * 通过订单编号查找订单id
     * @param orderNum
     * @return
     */
    int findOrderIdByOrderNum(String orderNum);
}
