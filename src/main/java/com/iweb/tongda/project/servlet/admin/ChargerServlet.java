package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Charger;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.ChargerDao;
import com.iweb.tongda.project.dao.impl.ChargerDaoImpl;
import com.iweb.tongda.project.util.RanUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-11 17:32
 */
@WebServlet("/jsp/admin/ChargerServlet")
public class ChargerServlet extends HttpServlet {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String CHARGER_LIST_PATH = "chargerManage/chargerList.jsp";
    private static final String CHARGER_ADD_PATH = "chargerManage/chargerAdd.jsp";
    private static final String CHARGER_UPDATE_PATH = "chargerManage/chargerEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取动作
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                chargerList(request, response);
                break;
            case "add":
                chargerAdd(request, response);
                break;
            case "edit":
                chargerEdit(request, response);
                break;
            case "update":
                chargerUpdate(request, response);
                break;
            case "del":
                chargerDel(request, response);
                break;
        }
    }

    /**
     * 删除单个配送员信息
     * @param request
     * @param response
     */
    private void chargerDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的配送员id
        int chargerId = Integer.parseInt(request.getParameter("id"));
        //创建ChargerDao对象
        ChargerDao chargerDao = new ChargerDaoImpl();
        if (chargerDao.delCharger(chargerId)){
            //删除成功
            request.setAttribute("chargerMessage","删除成功~");
            //调用列表函数
            chargerList(request, response);
        }else {
            //删除失败
            request.setAttribute("chargerMessage","删除失败!");
            //调用列表函数
            chargerList(request, response);
        }
    }

    /**
     * 更新配送员信息
     * @param request
     * @param response
     */
    private void chargerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取表单信息,并封装成Charger对象
        Charger charger = new Charger(
                Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"),
                request.getParameter("phone")
        );
        //创建ChargerDao对象
        ChargerDao chargerDao = new ChargerDaoImpl();
        if (chargerDao.updateCharger(charger)){
            //修改成功
            request.setAttribute("chargerMessage","修改成功~");
            //调用列表函数
            chargerList(request, response);
        }else {
            //修改失败
            request.setAttribute("chargerMessage","修改失败!");
            request.getRequestDispatcher(CHARGER_UPDATE_PATH).forward(request,response);
        }

    }

    /**
     * 将需要编辑的配送员信息传入修改页面
     * @param request
     * @param response
     */
    private void chargerEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台选中的配送员id
        int chargerId = Integer.parseInt(request.getParameter("id"));
        //创建ChargerDao对象
        ChargerDao chargerDao = new ChargerDaoImpl();
        //将信息存入request域中
        request.setAttribute("charger",chargerDao.getChargerById(chargerId));
        //转发到修改页面
        request.getRequestDispatcher(CHARGER_UPDATE_PATH).forward(request, response);
    }

    /**
     * 添加配送员
     * @param request
     * @param response
     */
    private void chargerAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台参数
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        //设置配送员编号 PSY+13个数字
        String number = RanUtil.getChargerNo();
        //封装成配送员对象
        Charger charger = new Charger(name, phone, number);
        //创建ChargerDao对象
        ChargerDao chargerDao = new ChargerDaoImpl();
        if (chargerDao.addCharger(charger)){
            //添加成功
            request.setAttribute("chargerMessage","添加成功~");
            //调用列表函数
            chargerList(request, response);
        }else {
            //添加失败
            request.setAttribute("chargerMessage","添加失败!");
            //转发到当前页面
            request.getRequestDispatcher(CHARGER_ADD_PATH).forward(request, response);
        }
    }

    /**
     * 配送员列表
     *
     * @param request
     * @param response
     */
    private void chargerList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建ChargerDao对象
        ChargerDao chargerDao = new ChargerDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,chargerDao.allChargerCount());
        //将数据存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("chargerList",chargerDao.getChargerByLike(pageBean));
        //转发到配送员列表页面
        request.getRequestDispatcher(CHARGER_LIST_PATH).forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
