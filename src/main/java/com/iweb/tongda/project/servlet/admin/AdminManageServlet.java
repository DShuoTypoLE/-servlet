package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.AdminDao;
import com.iweb.tongda.project.dao.impl.AdminDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-06 9:15
 */
@WebServlet("/jsp/admin/AdminManageServlet")
public class AdminManageServlet extends HttpServlet {
    //设置页面展示最大数据量为8
    private static final int MAX_PAGE_SIZE = 8;
    //设置管理员列表页面路径,因为地址上面有/jsp/admin，这里就不加了
    private static final String ADMINLIST_PATH = "adminManage/adminList.jsp";
    private static final String ADMINEDIT_PATH = "adminManage/adminEdit.jsp";
    private static final String ADMINLOGIN_PATH = "login.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取行为对象,进行接口分发
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                adminManageList(request,response);
                break;
            case "edit":
                adminEdit(request,response);
                break;
            case "update":
                adminUpdate(request,response);
                break;
            case "add":
                adminAdd(request,response);
                break;
            case "find":
                adminAjaxFindUser(request,response);
                break;
            case "del":
                adminDel(request,response);
                break;
            case "batDel":
                adminBatDel(request,response);
                break;
        }
    }

    /**
     * 管理员批量删除
     * @param request
     * @param response
     */
    private void adminBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台选中的ids
        String ids = request.getParameter("ids");
        //根据,分成数组
        String[] idsArr = ids.split(",");
        //下面要转成int类型
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArr.length; i++) {
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //下面创建AdminDao对象
        AdminDao adminDao = new AdminDaoImpl();
        if (adminDao.batDelAdmin(idsArrInt)){
            //批量删除成功
            request.setAttribute("adminMessage","删除成功~");
            //调用管理员列表功能
            adminManageList(request,response);
        }else {
            request.setAttribute("adminMessage","删除失败!");
            //调用管理员列表功能
            adminManageList(request,response);
        }
    }

    /**
     * 删除单个管理员信息
     * @param request
     * @param response
     */
    private void adminDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传过来的管理员id
        int adminId = Integer.parseInt(request.getParameter("id"));
        //接下来就是根据管理员id删除对应管理员信息
        //创建AdminDao对象
        AdminDao adminDao = new AdminDaoImpl();
        if (adminDao.delAdmin(adminId)){
            //删除成功
            request.setAttribute("adminMessage","删除成功~");
            //调用管理员列表功能
            adminManageList(request,response);
        }else {
            //删除成功
            request.setAttribute("adminMessage","删除失败!");
            //调用管理员列表功能
            adminManageList(request,response);
        }
    }

    /**
     * 验证管理员用户名是否可以使用
     * @param request
     * @param response
     */
    private void adminAjaxFindUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前台传递来的用户名是否存在
        //获取参数
        String username = request.getParameter("param");
        //获取键名
        String name = request.getParameter("name");
        //将用户名传入dao调用方法
        AdminDao adminDao = new AdminDaoImpl();

        //创建Json对象
        JSONObject json = new JSONObject();
        if (username == null || "".equals(username)) {
            json.put("info", "other");
        } else {
            if (adminDao.findAdminByUsername(username)) {
                json.put("info", "用户名已存在");
                json.put("status","n");
            } else {
                json.put("info", "用户名可以使用");
                json.put("status","y");
            }
        }
        //设置格式和编码
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json.toString());
    }

    /**
     * 添加管理员
     * @param request
     * @param response
     */
    private void adminAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前端参数
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        String name = request.getParameter("name");

        //封装成Admin对象
        Admin admin = new Admin(userName, passWord, name);
        //创建AdminDao对象
        AdminDao adminDao = new AdminDaoImpl();
        //添加数据库
        if (adminDao.addAdmin(admin)){
            //添加成功
            //存入响应信息
            request.setAttribute("adminMessage","添加成功~");
            //转发到管理员列表界面
            //request.getRequestDispatcher(ADMINLIST_PATH).forward(request,response);

            //这里直接调用列表展示功能
            adminManageList(request, response);
        }else{
            //添加失败
            //存入响应信息
            request.setAttribute("adminMessage","添加失败!");
            //转发到管理员列表界面
            //request.getRequestDispatcher(ADMINLIST_PATH).forward(request,response);
            //这里直接调用列表展示功能
            adminManageList(request, response);
        }
    }

    /**
     * 编辑界面更新管理员信息
     * @param request
     * @param response
     */
    private void adminUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递的参数
        int adminId = Integer.parseInt(request.getParameter("id"));
        String passWord = request.getParameter("passWord");
        String name = request.getParameter("name");
        //封装成对象存入数据库中，然后跳转到管理员列表界面
        Admin admin = new Admin(adminId,passWord,name);
        //创建AdminDao
        AdminDao adminDao = new AdminDaoImpl();
        //这里要考虑管理员可能修改自己的信息，那么此时修改完应该重新登陆
        //获取登录信息
        Admin adminUser = (Admin) request.getSession().getAttribute("adminUser");
        //如果修改的是当前登录的管理员信息，修改完后重新登陆
        //adminUser是没有id的,这里通过用户名来查找当前登录用户id
        int adminUserId = adminDao.findAdminIdByUsername(adminUser.getUserName());
        if (adminUserId == adminId){
            if (adminDao.updateAdminById(admin)){
                //更新成功
                //给出提示信息，并跳转到登陆界面
                //设置响应方式
                /*
                第一种写法，但是前后端代码融合了，不太好
                response.setContentType("text/html");
                response.getWriter().write(
                        "<script>" +
                        "alert('修改成功,请重新登录');" +
                        "window.location.href = '../../LoginOutServlet';" +
                        "</script>");
                 */
                response.getWriter().write("修改成功~");
            }else {
                //更新失败
                request.setAttribute("adminMessage","管理员信息更新失败，快联系作者找bug吧!");
                adminManageList(request, response);
            }
        }else {
            //修改其他管理员信息,正常修改完后跳转管理员列表界面
            if (adminDao.updateAdminById(admin)){
                //更新成功
                //给出提示信息，并转发到管理员列表界面
                request.setAttribute("adminMessage","管理员信息更新成功啦~");
                adminManageList(request, response);
            }else {
                //更新失败
                request.setAttribute("adminMessage","管理员信息更新失败，快联系作者找bug吧!");
                adminManageList(request, response);
            }
        }
    }

    /**
     * 管理员界面点击编辑携带信息跳转编辑页面
     * @param request
     * @param response
     */
    private void adminEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取管理员id
        int adminId = Integer.parseInt(request.getParameter("id"));
        //现在是把数据库中该管理员id对于的管理员对象查询到，然后传给前台
        //创建AdminDao对象
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = adminDao.findAdminById(adminId);

        //将信息存入request域中
        request.setAttribute("adminInfo",admin);
        //将信息转发到编辑页面
        request.getRequestDispatcher(ADMINEDIT_PATH).forward(request, response);
    }

    /**
     * 管理员管理列表展示
     * @param request
     * @param response
     */
    private void adminManageList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置当前页面值
        int curPage = 1;
        //得到页面page参数
        String page = request.getParameter("page");
        if (page != null){
            //说明进行了跳转,把获取到的值赋给curPage
            curPage = Integer.parseInt(page);
        }
        //创建AdminDao对象
        AdminDao adminDao = new AdminDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,adminDao.allAdminCount());
        //将需要的值存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("adminList",adminDao.adminList(pageBean));
        //将数据转发到管理员列表页面，并跳转过去
        request.getRequestDispatcher(ADMINLIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
