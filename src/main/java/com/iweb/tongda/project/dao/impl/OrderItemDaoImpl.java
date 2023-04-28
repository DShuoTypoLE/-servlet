package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.OrderItem;
import com.iweb.tongda.project.dao.OrderItemDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 14:26
 */
public class OrderItemDaoImpl implements OrderItemDao {
    /**
     * 添加订单项
     * @param orderItem
     */
    @Override
    public boolean addOrderItem(OrderItem orderItem) {
        String sql = "insert into s_orderitem(flowId,orderId,quantity) values(?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                orderItem.getFlowId(),
                orderItem.getOrderId(),
                orderItem.getQuantity());
        return i > 0 ? true:false;
    }

    /**
     * 通过订单id查询订单项
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> findOrderItemByOrderId(int orderId) {
        String sql = "select * from s_orderitem where orderId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, orderId);
        List<OrderItem> list = new ArrayList<>();
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                OrderItem orderItem = new OrderItem(map);
                list.add(orderItem);
            }
        }
        return list;
    }
}
