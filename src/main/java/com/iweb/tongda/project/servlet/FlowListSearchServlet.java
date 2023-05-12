package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.dao.impl.FlowDaoImpl;

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
 * @Date 2023-05-12 0:32
 */
@WebServlet("/FlowList2")
public class FlowListSearchServlet extends HttpServlet {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String FLOW_LIST_PATH = "jsp/flow/flowlist.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台鲜花名称
        String flowName = request.getParameter("searchname");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建FlowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage, MAX_PAGE_SIZE, flowDao.readFlowCountByLike(flowName));
        List<Flow> flowByLike = flowDao.findFlowByLike(pageBean, flowName);
        //将信息存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowByLike);
        request.setAttribute("title","搜索鲜花");
        //转发到鲜花列表界面
        request.getRequestDispatcher(FLOW_LIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
