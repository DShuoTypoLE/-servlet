package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.dao.CatalogDao;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.impl.CatalogDaoImpl;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;
import jdk.nashorn.internal.ir.CallNode;
import net.sf.json.JSONObject;

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
 * @Date 2023-04-26 11:54
 */
@WebServlet("/GetCatalog")
public class GetCatalogServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应格式
        response.setContentType("text/json;charset=utf-8");
        //创建json响应对象
        JSONObject json = new JSONObject();

        //创建catalog dao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        //catalogDao调用查询方法,查询所有分类
        List<Catalog> catalogList = catalogDao.getCatalog();

        //查询鲜花每个分类对应的数量,通过分类id
        //创建flowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //遍历分类,获取每个分类中的鲜花数量
        for (int i = 0; i < catalogList.size(); i++) {
            //获取分类
            Catalog catalog = catalogList.get(i);
            //查询数量
            long count = flowDao.flowReadCount(catalog.getCatalogId());
            //将数量设置到分类中
            catalog.setCatalogSize(count);
        }

        //把数据放到json中返回
        json.put("catalog",catalogList);
        //这里json是对象,要显式调用toSting()方法,不然会报错
        response.getWriter().write(json.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
