package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.*;
import com.iweb.tongda.project.dao.*;
import com.iweb.tongda.project.dao.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-04 22:48
 */
@WebServlet("/jsp/admin/OrderManageServlet")
public class OrderManageServlet extends HttpServlet {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String ORDERLIST_PATH = "orderManage/orderlist.jsp";
    private static final String ORDERDETAIL_PATH = "orderManage/orderDetail.jsp";
    private static final String ORDEROP_PATH = "orderManage/orderOp.jsp";
    private static final String ORDERCHARGER_PATH = "orderManage/orderCharger.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                orderList(request,response);
                break;
            case "detail":
                orderDetail(request,response);
                break;
            case "delete":
                orderDelete(request,response);
                break;
            case "search":
                orderSearch(request,response);
                break;
            case "processing":
                orderProcessing(request,response);
                break;
            case "setCharger":
                setCharger(request,response);
                break;
            case "updateCharger":
                updateCharger(request,response);
                break;
            case "ship":
                orderShip(request,response);
                break;
            case "search1":
                orderSearch1(request,response);
                break;
        }
    }

    /**
     * 待处理页面搜索
     * @param request
     * @param response
     */
    private void orderSearch1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //分页设置
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //获取前台传递来的搜素内容(订单编号)
        String orderNum = request.getParameter("ordername");
        OrderDao orderDao = new OrderDaoImpl();
        PageBean pageBean = null;
        //如果用户搜索时没有传入订单号，就全部搜索；传入订单号就按订单号搜索
        if (orderNum != null && orderNum != ""){
            //创建PageBean
            pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderDao.orderReadCountByStatusAndOrderNum(1,orderNum));
            //根据分页对象查询每页显示数据
            request.setAttribute("orderList",orderDao.orderListByStatusAndOrderNum(pageBean,1,orderNum));
        }else {
            orderProcessing(request, response);
            return;
        }
        request.setAttribute("pageBean",pageBean);
        request.getRequestDispatcher(ORDEROP_PATH).forward(request,response);
    }

    /**
     * 发货,修改货物状态
     * @param request
     * @param response
     */
    private void orderShip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
        String orderid = request.getParameter("id");
        OrderDao orderDao = new OrderDaoImpl();

        if (orderDao.orderStatus(Integer.parseInt(orderid),2)){
            request.setAttribute("orderMessage","一个订单操作成功~");
        }else {
            request.setAttribute("orderMessage","一个订单操作失败!");
        }
        //刷新列表
        orderProcessing(request, response);
    }

    /**
     * 将配送员真正的加入到订单信息当中
     * @param request
     * @param response
     */
    private void updateCharger(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取前台传来的信息
        //订单id
        int orderid = Integer.parseInt(request.getParameter("id"));
        //订单编号
        String orderNum = request.getParameter("orderNum");
        //配送员id,因为前台默认选中第一个列表配送员，所以不会为null
        int chargerid = Integer.parseInt(request.getParameter("charger"));
        //接下来就是把配送员信息加入到指定的订单表中的订单里
        //根据配送员id得到配送员信息
        ChargerDao chargerDao = new ChargerDaoImpl();
        Charger charger = chargerDao.getChargerById(chargerid);
        //这里不用判空,必有
        //把charge对象加入指定订单信息里
        OrderDao orderDao = new OrderDaoImpl();
        //根据订单id查找订单
        Order orderByOrderId = orderDao.findOrderByOrderId(orderid);
        orderByOrderId.setChargerid(chargerid);
        orderByOrderId.setChargername(charger.getName());
        orderByOrderId.setChargerphone(charger.getPhone());
        //更新数据库中订单信息
        if (orderDao.updateOrder(orderByOrderId)){
            //更新成功，就跳转到待处理订单列表页
            orderProcessing(request,response);
        }
    }

    /**
     * 设置配送员
     * @param request
     * @param response
     */
    private void setCharger(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
        String orderid = request.getParameter("id");
        //查询所有配送员
        OrderDao orderDao = new OrderDaoImpl();
        Order orderByOrderId = orderDao.findOrderByOrderId(Integer.parseInt(orderid));

        ChargerDao chargerDao = new ChargerDaoImpl();
        List<Charger> chargers = chargerDao.getCharger();

        request.setAttribute("charger",chargers);
        request.setAttribute("order",orderByOrderId);
        request.getRequestDispatcher(ORDERCHARGER_PATH).forward(request,response);
    }

    /**
     * 订单处理页面
     * @param request
     * @param response
     */
    private void orderProcessing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //分页设置
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        OrderDao orderDao = new OrderDaoImpl();
        //需要获取未发货订单的数量,根据订单状态查询,分页数据
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderDao.orderReadCountByStatus(1));

        request.setAttribute("pageBean",pageBean);
        //根据分页状态,查询订单列表
        request.setAttribute("orderList",orderDao.orderListByStatus(pageBean,1));
        request.getRequestDispatcher(ORDEROP_PATH).forward(request,response);
    }

    /**
     * 订单搜索
     * @param request
     * @param response
     */
    private void orderSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //分页设置
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //获取前台传递来的搜素内容(订单编号)
        String orderNum = request.getParameter("ordername");
        OrderDao orderDao = new OrderDaoImpl();
        PageBean pageBean = null;
        //如果用户搜索时没有传入订单号，就全部搜索；传入订单号就按订单号搜索
        if (orderNum != null){
            //创建PageBean
            pageBean = new PageBean(curPage,MAX_PAGE_SIZE,orderDao.orderCountByOrderNum(orderNum));
            //根据分页对象查询每页显示数据
            request.setAttribute("orderList",orderDao.orderListByOrderNum(pageBean,orderNum));
        }else {
            orderList(request, response);
            return;
        }
        request.setAttribute("pageBean",pageBean);
        request.getRequestDispatcher(ORDERLIST_PATH).forward(request,response);
    }

    /**
     * 删除订单
     * @param request
     * @param response
     */
    private void orderDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        OrderDao orderDao = new OrderDaoImpl();
        if (id != null && id != "") {
            orderDao.deleteOrder(Integer.parseInt(id));
            orderDao.deleteOrderItem(Integer.parseInt(id));
        }
        orderList(request, response);
    }

    /**
     * 后台订单详情
     * @param request
     * @param response
     */
    private void orderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递的订单id
        int orderId = Integer.parseInt(request.getParameter("id"));

        OrderDao orderDao = new OrderDaoImpl();
        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        UserDao userDao = new UserDaoImpl();
        FlowDao flowDao = new FlowDaoImpl();

        //通过订单id查询订单
        Order order = orderDao.findOrderByOrderId(orderId);
        //通过order对象查询userid,然后根据userid查询User对象
        User user = userDao.findUserByUserId(order.getUserId());
        order.setUser(user);
        //获取订单项信息
        List<OrderItem> orderItem = orderItemDao.findOrderItemByOrderId(orderId);
        order.setoItem(orderItem);

        //遍历订单项中的商品
        for (OrderItem item : order.getoItem()) {
            item.setFlow(flowDao.findFlowById(item.getFlowId()));
        }
        request.setAttribute("order",order);
        request.getRequestDispatcher(ORDERDETAIL_PATH).forward(request, response);
    }

    /**
     * 获取后台订单列表
     * @param request
     * @param response
     */
    private void orderList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //分页设置
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建一个OrderDao，获取所有订单数量
        OrderDao orderDao = new OrderDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage, MAX_PAGE_SIZE, orderDao.allOrderCount());
        //根据分页对象查询第一页显示数据
        request.setAttribute("orderList",orderDao.orderList(pageBean));
        request.setAttribute("pageBean",pageBean);
        request.getRequestDispatcher(ORDERLIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
