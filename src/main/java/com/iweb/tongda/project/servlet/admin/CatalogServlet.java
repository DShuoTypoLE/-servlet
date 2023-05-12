package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.CatalogDao;
import com.iweb.tongda.project.dao.impl.CatalogDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-11 14:38
 */
@WebServlet("/jsp/admin/CatalogServlet")
public class CatalogServlet extends HttpServlet {
    //设置分页时每页最大页数
    private static final int MAX_PAGE_SIZE = 8;
    private static final String CATALOG_LIST_PATH = "flowManage/catalogList.jsp";
    private static final String CATALOG_ADD_PATH = "flowManage/catalogAdd.jsp";
    private static final String CATALOG_UPDATE_PATH = "flowManage/catalogEdit.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台行为对象
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                catalogList(request,response);
                break;
            case "add":
                catalogAdd(request,response);
                break;
            case "find":
                catalogFind(request,response);
                break;
            case "edit":
                catalogEdit(request,response);
                break;
            case "update":
                catalogUpdate(request,response);
                break;
            case "del":
                catalogDel(request,response);
                break;
            case "batDel":
                catalogBatDel(request,response);
                break;
        }
    }

    /**
     * 批量删除
     * @param request
     * @param response
     */
    private void catalogBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端传入的ids
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArrInt.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //设置删除状态标志
        boolean flag = true;
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        for (int catalogId : idsArrInt) {
            if (!catalogDao.delCatalog(catalogId)){
                flag = false;
            }
        }
        if (flag){
            //删除成功
            //将提示信息存入request域中
            request.setAttribute("catalogMessage","批量删除成功~");
            //转发列表函数
            catalogList(request, response);
        }else {
            //删除失败
            //将提示信息存入request域中
            request.setAttribute("catalogMessage","批量删除失败！");
            //转发列表函数
            catalogList(request, response);
        }
    }

    /**
     * 删除单个
     * @param request
     * @param response
     */
    private void catalogDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递过来的分类id
        int catalogId = Integer.parseInt(request.getParameter("id"));
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        if (catalogDao.delCatalog(catalogId)){
            //删除成功
            //将提示信息存入request域中
            request.setAttribute("catalogMessage","删除成功~");
            //转发到列表页面
            catalogList(request, response);
        }else {
            //删除失败
            //将提示信息存入request域中
            request.setAttribute("catalogMessage","删除失败！");
            //转发到列表页面
            catalogList(request, response);
        }
    }

    /**
     * 修改分类信息
     * @param request
     * @param response
     */
    private void catalogUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取表单提交的参数
        int catalogId = Integer.parseInt(request.getParameter("id"));
        String catalogName = request.getParameter("catalogName");
        //封装成Catalog对象
        Catalog catalog = new Catalog(catalogId, catalogName);
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        if (catalogDao.updateCatalog(catalog)){
            //修改成功
            //将提示信息存入request域中
            request.setAttribute("catalogMessage","修改成功~");
            //转发到列表页面
            catalogList(request, response);
        }else {
            //修改失败
            request.setAttribute("catalogMessage","修改失败!");
            //转发到修改页面
            request.getRequestDispatcher(CATALOG_UPDATE_PATH).forward(request,response);
        }
    }

    /**
     * 为修改页面传递信息
     * @param request
     * @param response
     */
    private void catalogEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String catalogId = request.getParameter("id");
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        Catalog catalog = catalogDao.getCatalogById(Integer.parseInt(catalogId));
        //存入request域中
        request.setAttribute("catalog",catalog);
        //转发到编辑页面
        request.getRequestDispatcher(CATALOG_UPDATE_PATH).forward(request, response);
    }

    /**
     * 分类名称验证
     * @param request
     * @param response
     */
    private void catalogFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取参数
        String catalogName = request.getParameter("param");
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        //创建JSON对象
        JSONObject json = new JSONObject();
        if (catalogDao.findCatalog(catalogName)){
            //分类名存在,也就是不可用
            json.put("info","分类名已存在!");
            json.put("status","n");
        }else {
            //分类名不存在，可以使用
            json.put("info","分类名可以使用~");
            json.put("status","y");
        }
        //响应
        response.getWriter().write(json.toString());
    }

    /**
     * 添加分类
     * @param request
     * @param response
     */
    private void catalogAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取表单提交的数据
        String catalogName = request.getParameter("catalogName");
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        if (catalogDao.addCatalog(catalogName)){
            //添加分类成功
            //在request域中存入提示信息
            request.setAttribute("catalogMessage","添加成功~");
            //调用列表函数
            catalogList(request, response);
        }else {
            //添加失败
            //在request域中存入提示信息
            request.setAttribute("catalogMessage","添加失败!");
            //这里还是在添加页面显示信息
            request.getRequestDispatcher(CATALOG_ADD_PATH).forward(request, response);
        }
    }

    /**
     * 分类管理列表
     * @param request
     * @param response
     */
    private void catalogList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从数据库中获取鲜花列表对象并放入request域中
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建CatalogDao对象
        CatalogDao catalogDao = new CatalogDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,catalogDao.allCatalogCount());
        //将信息存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("catalogList",catalogDao.getCatalogByPage(pageBean));
        //转发到分类列表界面
        request.getRequestDispatcher(CATALOG_LIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
