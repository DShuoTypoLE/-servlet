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

    /**
     * 所有订单数量
     * @return
     */
    long allOrderCount();

    /**
     * 根据分页对象查询分页数据
     * @param pageBean
     * @return
     */
    List<Order> orderList(PageBean pageBean);

    /**
     * 通过订单id查询订单
     * @param orderId
     * @return
     */
    Order findOrderByOrderId(int orderId);

    /**
     * 根据订单id,删除订单
     * @param id
     * @return
     */
    boolean deleteOrder(int id);

    /**
     * 根据订单id，删除订单项
     * @param id
     * @return
     */
    boolean deleteOrderItem(int id);

    /**
     * 查询符合搜索内容的订单总数量
     * @param orderNum
     * @return
     */
    long orderCountByOrderNum(String orderNum);

    /**
     * 查询符合搜索条件并且分页的所有订单
     * @param pageBean
     * @param orderNum
     * @return
     */
    List<Order> orderListByOrderNum(PageBean pageBean, String orderNum);

    /**
     * 根据订单状态统计订单状况
     * @param status
     * @return
     */
    long orderReadCountByStatus(int status);

    /**
     * 根据分页条件、订单状态查询订单列表
     * @param pageBean
     * @param status
     * @return
     */
    List<Order> orderListByStatus(PageBean pageBean, int status);

    /**
     * 根据订单id更新订单,这里通过对象来获取订单id
     * @param order
     * @return
     */
    boolean updateOrder(Order order);

    /**
     * 更改订单状态
     * @param orderId
     * @param status
     * @return
     */
    boolean orderStatus(int orderId, int status);

    /**
     * 根据订单状态和订单编号查询总记录数，查已提交的订单
     * @param status
     * @param orderNum
     * @return
     */
    long orderReadCountByStatusAndOrderNum(int status, String orderNum);

    /**
     * 根据订单状态和订单编号和分页查询待处理列表
     * @param pageBean
     * @param status
     * @param orderNum
     * @return
     */
    List<Order> orderListByStatusAndOrderNum(PageBean pageBean, int status, String orderNum);

}
