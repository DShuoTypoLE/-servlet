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
import java.util.ArrayList;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 15:28
 */
@WebServlet("/FlowList")
public class FlowListServlet extends HttpServlet {
    //定义一页最多显示多少条数据
    private static final int MAX_PAGE_SIZE = 12;
    private static final String FLOWLIST_PATH = "jsp/flow/flowlist.jsp";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断请求过来的参数是否携带分类的id,如果不携带,就是查询所有数据
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "list":
                flowList(request, response);
                break;
        }
    }

    /**
     * //查询所有鲜花(设置分页)
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void flowList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建flowDao对象
        FlowDao flowDao = new FlowDaoImpl();
        //设置分页对象,初始页 默认为1 每页数量为12 总数据量(数据库)
        //初始页 默认为1
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            //表示分页被点击了,点击了第几页就把初始页设置为几
            curPage = Integer.parseInt(page);
        }

        //创建一个分页对象
        PageBean pageBean = null;
        //创建一个所有鲜花的集合
        List<Flow> flowList = new ArrayList<>();

        //获取携带的分类id
        String catalogIdStr = request.getParameter("catalogId");
        //如果有id就查询对应的鲜花
        if (catalogIdStr != null) {
            //获取id
            int catalogId = Integer.parseInt(catalogIdStr);
            //创建分页对象
            pageBean = new PageBean(curPage, MAX_PAGE_SIZE, flowDao.flowReadCount(catalogId));
            //查询每页显示的鲜花,需要传入分页对象和分类id
            flowList = flowDao.typeFlowList(pageBean, catalogId);

            //如果集合中有数据的话
            if (flowList.size() > 0) {
                //获取鲜花分类的名称,并展示在头部
                request.setAttribute("title", flowList.get(0).getCatalog().getCatalogName());
            }
        } else {
            //没有id,查询所有
            pageBean = new PageBean(curPage,MAX_PAGE_SIZE,flowDao.allFlowList());

            //通过分页对象查询确定将来页面上具体显示哪些鲜花
            flowList = flowDao.flowList(pageBean);

            request.setAttribute("title","所有鲜花");
        }
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("flowList",flowList);
        request.getRequestDispatcher(FLOWLIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
