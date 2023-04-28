package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.*;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.OrderDao;
import com.iweb.tongda.project.dao.OrderItemDao;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;
import com.iweb.tongda.project.dao.impl.OrderDaoImpl;
import com.iweb.tongda.project.dao.impl.OrderItemDaoImpl;
import com.iweb.tongda.project.util.DateUtil;
import com.iweb.tongda.project.util.RanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 14:12
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
    private static final String CART_PATH = "jsp/flow/cart.jsp";
    private static final String ORDER_SUCCESS_PATH = "jsp/flow/ordersuccess.jsp";
    private static final String LOGIN_PATH = "jsp/flow/login.jsp";
    private static final int MAX_SIZE = 5;
    private static final String MYORDER_PATH = "jsp/flow/myorderlist.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "subOrder":
                subOrder(request,response);
                break;
            case "list":
                myOrderList(request,response);
                break;
        }
    }

    /**
     * 查看我的订单列表
     * @param request
     * @param response
     */
    private void myOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取session中的用户,要查询的是哪个用户的订单
        User user = (User) request.getSession().getAttribute("landing");

        //如果session没有用户就跳转到登录页面
        if (user == null) {
            response.sendRedirect(LOGIN_PATH);
        }else {
            //通过用户查询订单
            OrderDao orderDao = new OrderDaoImpl();
            OrderItemDao orderItemDao = new OrderItemDaoImpl();
            FlowDao flowDao = new FlowDaoImpl();

            //设置分页
            //初始页
            int curPage = 1;
            String page = request.getParameter("page");
            if (page != null){
                curPage = Integer.parseInt(page);
            }
            //创建分页对象 初始页,最大数量,用户所有订单数据
            PageBean pageBean = new PageBean(curPage, MAX_SIZE, orderDao.orderReadCount(user.getUserId()));

            //根据分页对象,查询每页显示多少数据
            List<Order> orderList = orderDao.myOrderList(pageBean, user.getUserId());

            //遍历订单,通过订单编号查询订单项加在order中,返回给前台
            for (Order order : orderList) {
                //根据订单id查询订单项
                order.setoItem(orderItemDao.findOrderItemByOrderId(order.getOrderId()));
                //将每个订单中的商品也放到OrderItem中
                List<OrderItem> orderItems = order.getoItem();
                for (OrderItem orderItem : orderItems) {
                    orderItem.setFlow(flowDao.findFlowById(orderItem.getFlowId()));
                }
            }
            request.setAttribute("pageBean",pageBean);
            request.setAttribute("orderList",orderList);
            request.getRequestDispatcher(MYORDER_PATH).forward(request, response);
        }
    }

    /**
     * 提交订单方法
     * @param request
     * @param response
     */
    private void subOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单相关的数据
        //商品数据、人物数据在session中
        //收货人信息,从前台拿
        HttpSession session = request.getSession();
        //session中的购物车信息
        Cart shopCart = (Cart) session.getAttribute("shopCart");
        //session中的用户信息
        User user = (User) session.getAttribute("landing");

        //生成订单信息、订单日期
        String orderNum = RanUtil.getOrderNum();
        String orderDate = DateUtil.show();
        //获取前台传来的收货人信息
        String shpeople = request.getParameter("shpeople");
        String shphone = request.getParameter("shphone");
        String shaddress = request.getParameter("shaddress");

        //创建订单对象
        Order order = new Order();
        //创建OrderDao,orderItemDao对象
        OrderDao orderDao = new OrderDaoImpl();
        OrderItemDao orderItemDao = new OrderItemDaoImpl();

        //给order对象设置值
        order.setShpeople(shpeople);
        order.setShphone(shphone);
        order.setShaddress(shaddress);
        order.setOrderNum(orderNum);
        order.setOrderDate(orderDate);
        order.setMoney(shopCart.getTotPrice());
        //订单默认状态是未发货,值为1
        order.setOrderStatus(1);
        order.setUserId(user.getUserId());

        //添加订单
        if (orderDao.addOrder(order)){
            //设置订单的订单编号,订单会用到
            order.setOrderId(orderDao.findOrderIdByOrderNum(orderNum));

            //遍历购物车的订单项数据
            //Set<Map.Entry<Integer, CartItem>> entries = shopCart.getMap().entrySet();
            //for (Map.Entry<Integer, CartItem> entry : entries) {
            //    //通过购物车中的数据创建订单项(将来是我的订单)
            //    OrderItem orderItem = new OrderItem();
            //    orderItem.setFlowId(entry.getKey());
            //    orderItem.setOrderId(order.getOrderId());
            //    orderItem.setQuantity(entry.getValue().getQuantity());
            //    //把订单项加入数据库
            //    orderItemDao.addOrderItem(orderItem);
            //}
            //stream流的写法
            shopCart.getMap().forEach((key,value)->{
                OrderItem orderItem = new OrderItem();
                orderItem.setFlowId(key);
                orderItem.setOrderId(order.getOrderId());
                orderItem.setQuantity(value.getQuantity());
                //把订单项加入数据库
                orderItemDao.addOrderItem(orderItem);
            });
            //订单提交后,信息保存好,清空购物车,返回到成功页面
            session.removeAttribute("shopCart");
            request.setAttribute("orderNum",order.getOrderNum());
            request.setAttribute("money",order.getMoney());
            request.getRequestDispatcher(ORDER_SUCCESS_PATH).forward(request, response);
        }else {
            request.setAttribute("suberr","订单提交失败,请重新提交~");
            request.getRequestDispatcher(CART_PATH).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
