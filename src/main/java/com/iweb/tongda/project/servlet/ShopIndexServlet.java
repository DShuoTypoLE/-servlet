package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.Address;
import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.dao.AddressDao;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.impl.AddressDaoImpl;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 14:24
 */
@WebServlet("/ShopIndex")
public class ShopIndexServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查找返回地区信息,加入到session中,展示在页面右上角
        AddressDao  addressDao = new AddressDaoImpl();
        //查询地址方法
        Address address = addressDao.getAddress();
        //放入session中
        request.getSession().setAttribute("address",address);

        //响应类型
        response.setContentType("text/json;charset=utf-8");
        //创建json对象
        JSONObject json = new JSONObject();
        //查询鲜花列表,返回4个鲜花作为推荐鲜花
        FlowDao flowDao = new FlowDaoImpl();
        //根据数量查询鲜花列表
        List<Flow> recFlows = flowDao.flowList(4);
        json.put("recFlows",recFlows);
        //查询4个新品鲜花
        List<Flow> newFlows = flowDao.newFlowList(4);
        json.put("newFlows",newFlows);

        //响应回前台
        PrintWriter writer = response.getWriter();
        //print可以直接输出对象,所以不加toString()方法也可以
        writer.print(json);
        //这个不加测试了一下也没有什么问题
        writer.flush();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
