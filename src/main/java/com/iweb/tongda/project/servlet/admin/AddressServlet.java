package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Address;
import com.iweb.tongda.project.dao.AddressDao;
import com.iweb.tongda.project.dao.impl.AddressDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-08 9:36
 */
@WebServlet("/jsp/admin/AddressServlet")
public class AddressServlet extends HttpServlet {
    private static final String ADDRESSUPDATE_PATH = "addressManage/addressEdit.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取行为对象
        String action = request.getParameter("action");
        switch (action) {
            case "edit":
                addressEdit(request,response);
                break;
            case "update":
                addressUpdate(request,response);
                break;
        }
    }

    /**
     * 得到从页面选中的地址信息，然后修改数据库
     * @param request
     * @param response
     */
    private void addressUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台表单参数
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        //封装成address对象
        Address address = new Address(province, city);
        //创建AddressDao对象
        AddressDao addressDao = new AddressDaoImpl();
        if (addressDao.updateAddress(address)){
            //修改成功
            //存入提示信息到request域中
            request.setAttribute("addressMessage","修改成功~");
            //转发到地区编辑页面
            request.getRequestDispatcher(ADDRESSUPDATE_PATH).forward(request,response);
        }else {
            //修改失败
            //存入提示信息到request域中
            request.setAttribute("addressMessage","修改失败!");
            //转发到地区编辑页面
            request.getRequestDispatcher(ADDRESSUPDATE_PATH).forward(request,response);
        }
    }

    /**
     * 从数据库获取地区信息并转发到修改地区页面
     * @param request
     * @param response
     */
    private void addressEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建AddressDao对象
        AddressDao addressDao = new AddressDaoImpl();
        //获取数据库地区数据
        Address address = addressDao.getAddress();
        //存入request域中
        request.setAttribute("address",address);
        //转发到地址修改页面
        request.getRequestDispatcher(ADDRESSUPDATE_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
