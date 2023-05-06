package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.Cart;
import com.iweb.tongda.project.bean.CartItem;
import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 11:08
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final String CART_PATH = "jsp/flow/cart.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递过来的action
        String action = request.getParameter("action");
        //判断
        switch (action) {
            case "add":
                addToCart(request,response);
                break;
            case "delItem":
                deleteFlow(request,response);
                break;
            case "delAll":
                deleteAll(request,response);
                break;
            case "changeIn":
                changeIn(request,response);
                break;
        }
    }

    /**
     * 购物车数量变化
     * @param request
     * @param response
     */
    private void changeIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前台传递的flowId和quantity
        int flowId = Integer.parseInt(request.getParameter("flowId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        //获取session中的Cart对象
        HttpSession session = request.getSession();
        Cart shopCart = (Cart) session.getAttribute("shopCart");
        //获取到购物车中对应的鲜花对象
        //获取购物车标题对象
        CartItem cartItem = shopCart.getMap().get(flowId);
        //将cartItem对象中的值换成新的数量
        cartItem.setQuantity(quantity);
        //将新的数值返回到前台
        JSONObject json = new JSONObject();
        //新的总价
        json.put("totPrice",shopCart.getTotPrice());
        //新的总数
        json.put("totQuan",shopCart.getTotQuan());
        //新的小计
        json.put("subtotal",cartItem.getSubtotal());
        //新的数量
        json.put("quantity",cartItem.getQuantity());
        response.getWriter().write(json.toString());
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     */
    private void deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取session中的购物车对象
        request.getSession().removeAttribute("shopCart");
        response.sendRedirect(CART_PATH);
    }

    /**
     * 在购物车中删除单个商品
     * @param request
     * @param response
     */
    private void deleteFlow(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取要删除鲜花的id
        int flowId = Integer.parseInt(request.getParameter("id"));
        //获取session中的购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("shopCart");
        //判断购物车中是否包含这个鲜花,如果包含就把信息移除
        if (cart.getMap().containsKey(flowId)){
            cart.getMap().remove(flowId);
        }
        response.sendRedirect(CART_PATH);
    }

    /**
     * 添加购物车方法
     * @param request
     * @param response
     */
    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将shopCart信息填上然后返回
        //先获当前商品信息,获取前台传递来的鲜花id
        String flowId = request.getParameter("flowId");
        //通过鲜花id查询鲜花对象
        FlowDao flowDao = new FlowDaoImpl();
        Flow flow = flowDao.findFlowById(Integer.parseInt(flowId));
        //获取session中的shopCart内容,有的话就将新的放进去
        HttpSession session = request.getSession();
        //如果没有就创建一个购物车对象,然后将信息放进去
        Cart shopCart = (Cart) session.getAttribute("shopCart");

        //判断
        if (shopCart == null) {
            //如果购物车为null
            //创建一个购物车对象,加入session中
            shopCart = new Cart();
            //把shopCart放入session
            session.setAttribute("shopCart",shopCart);
        }
        //把鲜花对象加入到购物车中
        shopCart.addFlow(flow);

        //返回购物车数量
        response.getWriter().write(shopCart.getTotQuan());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
