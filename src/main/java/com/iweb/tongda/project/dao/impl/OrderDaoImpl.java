package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Order;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.OrderDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 14:26
 */
public class OrderDaoImpl implements OrderDao {
    /**
     * 添加订单
     * @param order
     * @return
     */
    @Override
    public boolean addOrder(Order order) {
        String sql = "insert into s_order(orderNum,userId,orderDate,money,orderStatus," +
                "shpeople,shphone,shaddress) values(?,?,?,?,?,?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                order.getOrderNum(),
                order.getUserId(),
                order.getOrderDate(),
                order.getMoney(),
                order.getOrderStatus(),
                order.getShpeople(),
                order.getShphone(),
                order.getShaddress());

        return i > 0 ? true: false;
    }

    /**
     * 根据用户id查询订单数量
     * @param userId
     * @return
     */
    @Override
    public long orderReadCount(int userId) {
        String sql = "select count(1) as count from s_order where userId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, userId);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 查询我的订单列表
     * @param pageBean
     * @param userId
     * @return
     */
    @Override
    public List<Order> myOrderList(PageBean pageBean, int userId) {
        String sql = "select * from s_order where userId = ? order by orderDate desc limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,userId,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Order> list = new ArrayList<>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                Order order = new Order(map);
                list.add(order);
            }
        }
        return list;
    }

    /**
     * 通过订单编号查找订单id
     * @param orderNum
     * @return
     */
    @Override
    public int findOrderIdByOrderNum(String orderNum) {
        String sql = "select orderId from s_order where orderNum = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, orderNum);
        int orderId = 0;
        if (mapList.size() > 0) {
            orderId = (int) mapList.get(0).get("orderId");
        }
        return orderId;
    }
}
