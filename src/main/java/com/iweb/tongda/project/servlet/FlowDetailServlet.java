package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 16:32
 */
@WebServlet("/flowdetail")
public class FlowDetailServlet extends HttpServlet {

    private static final String DETAIL_PATH = "jsp/flow/flowdetails.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递的鲜花的id
        int flowId = Integer.parseInt(request.getParameter("flowId"));
        FlowDao flowDao = new FlowDaoImpl();

        //根据鲜花id查询到鲜花的数据,放入request域中
        request.setAttribute("flowInfo",flowDao.findFlowById(flowId));

        request.getRequestDispatcher(DETAIL_PATH).forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
