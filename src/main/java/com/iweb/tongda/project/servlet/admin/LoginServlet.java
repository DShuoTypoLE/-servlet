package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.dao.AdminDao;
import com.iweb.tongda.project.dao.impl.AdminDaoImpl;

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
 * @Date 2023-05-04 21:43
 */
@WebServlet("/jsp/admin/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台数据
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //创建一个admin对象
        Admin admin = new Admin(userName, passWord);
        AdminDao adminDao = new AdminDaoImpl();

        List<String> list = new ArrayList<String>();
        //如果管理员存在
        if(adminDao.adminLogin(admin)){
            request.getSession().setAttribute("adminUser",admin);
            response.sendRedirect("index.jsp");
            return;
        }else {
            list.add("用户名或密码错误!");
        }
        request.setAttribute("infoList",list);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
