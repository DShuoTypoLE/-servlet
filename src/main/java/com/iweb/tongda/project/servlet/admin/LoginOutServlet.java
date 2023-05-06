package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-06 8:28
 */
@WebServlet("/LoginOutServlet")
public class LoginOutServlet extends HttpServlet {
    private static final String ADMIN_INDEX_PATH = "jsp/admin/index.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session中的管理员信息，然后删掉即可
        Admin adminUser = (Admin) request.getSession().getAttribute("adminUser");
        if (adminUser != null){
            request.getSession().removeAttribute("adminUser");
            response.sendRedirect(ADMIN_INDEX_PATH);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
