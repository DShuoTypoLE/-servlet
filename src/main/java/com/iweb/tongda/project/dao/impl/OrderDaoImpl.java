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
     *
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

        return i > 0 ? true : false;
    }

    /**
     * 根据用户id查询订单数量
     *
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
     *
     * @param pageBean
     * @param userId
     * @return
     */
    @Override
    public List<Order> myOrderList(PageBean pageBean, int userId) {
        String sql = "select * from s_order where userId = ? order by orderDate desc limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, userId,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Order> list = new ArrayList<>();
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Order order = new Order(map);
                list.add(order);
            }
        }
        return list;
    }

    /**
     * 通过订单编号查找订单id
     *
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

    /**
     * 所有订单数量
     *
     * @return
     */
    @Override
    public long allOrderCount() {
        String sql = "select count(*) as count from s_order";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页对象查询分页数据
     *
     * @param pageBean
     * @return
     */
    @Override
    public List<Order> orderList(PageBean pageBean) {
        String sql = "select * from s_order order by orderDate desc limit ? , ?";
        List<Order> list = new ArrayList<>();
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Order order = new Order(map);
                list.add(order);
            }
        }
        return list;
    }

    /**
     * 通过订单id查询订单
     *
     * @param orderId
     * @return
     */
    @Override
    public Order findOrderByOrderId(int orderId) {
        String sql = "select * from s_order where orderId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, orderId);
        Order order = null;
        if (mapList.size() > 0) {
            order = new Order(mapList.get(0));
        }
        return order;
    }

    /**
     * 根据订单id,删除订单
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteOrder(int id) {
        String sql = "delete from s_order where orderId = ?";
        int i = DbUtil.executeUpdate(sql, id);
        return i > 0 ? true : false;
    }

    /**
     * 根据订单id，删除订单项
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteOrderItem(int id) {
        String sql = "delete from s_orderitem where orderId = ?";
        int i = DbUtil.executeUpdate(sql, id);
        return i > 0 ? true : false;
    }

    /**
     * 查询符合搜索内容的订单总数量
     *
     * @param orderNum
     * @return
     */
    @Override
    public long orderCountByOrderNum(String orderNum) {
        String sql = "select count(*) as count from s_order where orderNum like '%" + orderNum + "%'";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);

        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 查询符合搜索条件并且分页的所有订单
     *
     * @param pageBean
     * @param orderNum
     * @return
     */
    @Override
    public List<Order> orderListByOrderNum(PageBean pageBean, String orderNum) {
        String sql = "select * from s_order where orderNum like '%" + orderNum + "%' limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Order> list = new ArrayList<>();
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Order order = new Order(map);
                list.add(order);
            }
        }
        return list;
    }

    /**
     * 根据订单状态统计订单状况
     *
     * @param status
     * @return
     */
    @Override
    public long orderReadCountByStatus(int status) {
        String sql = "select count(*) as count from s_order where orderStatus = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, status);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页条件、订单状态查询订单列表
     *
     * @param pageBean
     * @param status
     * @return
     */
    @Override
    public List<Order> orderListByStatus(PageBean pageBean, int status) {
        String sql = "select * from s_order where orderStatus = ? order by orderDate desc limit ? ,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                status,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Order> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Order order = new Order(map);
            list.add(order);
        }
        return list;
    }

    /**
     * 根据订单id更新订单,这里通过对象来获取订单id
     *
     * @param order
     * @return
     */
    @Override
    public boolean updateOrder(Order order) {
        String sql = "update s_order set chargerid = ?,chargername = ?,chargerphone = ? " +
                "where orderId = ?";
        int i = DbUtil.executeUpdate(sql,
                order.getChargerid(),
                order.getChargername(),
                order.getChargerphone(),
                order.getOrderId());
        return i > 0 ? true : false;
    }

    /**
     * 更改订单状态
     *
     * @param orderId
     * @param status
     * @return
     */
    @Override
    public boolean orderStatus(int orderId, int status) {
        String sql = "update s_order set orderStatus = ? where orderId = ?";
        int i = DbUtil.executeUpdate(sql,
                status,
                orderId);
        return i > 0 ? true: false;
    }

    /**
     * 根据订单状态和订单编号查询总记录数，查已提交的订单
     * @param status
     * @param orderNum
     * @return
     */
    @Override
    public long orderReadCountByStatusAndOrderNum(int status, String orderNum) {
        String sql = "select count(*) as count from s_order where orderStatus = ? and " +
                "orderNum like '%" + orderNum + "%'";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, status);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") :0;
    }

    /**
     * 根据订单状态和订单编号和分页查询待处理列表
     * @param pageBean
     * @param status
     * @param orderNum
     * @return
     */
    @Override
    public List<Order> orderListByStatusAndOrderNum(PageBean pageBean, int status, String orderNum) {
        String sql = "select * from s_order where orderStatus = ? and " +
                "orderNum like '%" + orderNum + "%' limit ? , ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                status,
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
}
