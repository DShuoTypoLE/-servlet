package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.bean.User;
import com.iweb.tongda.project.dao.UserDao;
import com.iweb.tongda.project.dao.impl.UserDaoImpl;
import com.iweb.tongda.project.util.SaltMD5Util;
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
 * @Date 2023-05-06 19:19
 */
@WebServlet("/jsp/admin/UserManageServlet")
public class UserManageServlet extends HttpServlet {
    //设置页面展示最大数据量为8
    private static final int MAX_PAGE_SIZE = 8;
    private static final String USERLIST_PATH = "userManage/userList.jsp";
    private static final String USERDETAIL_PATH = "userManage/userDetail.jsp";
    private static final String USERUPDATE_PATH = "userManage/userEdit.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取动作对象
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                userList(request,response);
                break;
            case "detail":
                userDetail(request,response);
                break;
            case "add":
                userAdd(request,response);
                break;
            case "find":
                userAjaxUrlFind(request,response);
                break;
            case "edit":
                userEdit(request,response);
                break;
            case "update":
                userUpdate(request,response);
                break;
            case "del":
                userDel(request,response);
                break;
            case "batDel":
                userBatDel(request,response);
                break;
            case "search":
                userSearchLike(request,response);
                break;
        }
    }

    /**
     * 模糊查询
     * @param request
     * @param response
     */
    private void userSearchLike(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传过来的用户名参数
        String username = request.getParameter("username");
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null){
            curPage = Integer.parseInt(page);
        }
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();

        if (username != null && !"".equals(username)){
            //用户名不为空则进行模糊查询
            //创建分页对象
            PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,userDao.readUserCountByLike(username));
            //根据分页对象进行模糊查询
            List<User> userByLike = userDao.findUserByLike(pageBean, username);
            //将信息存入request域中
            request.setAttribute("userList",userByLike);
            request.setAttribute("pageBean",pageBean);
            //转发到用户列表界面
            request.getRequestDispatcher(USERLIST_PATH).forward(request,response);
        }else {
            //用户名为空则调用用户列表函数
            userList(request,response);
        }
    }

    /**
     * 用户批量删除
     * @param request
     * @param response
     */
    private void userBatDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台用户id组
        String ids = request.getParameter("ids");
        String[] idsArr = ids.split(",");
        int[] idsArrInt = new int[idsArr.length];
        for (int i = 0; i < idsArr.length; i++) {
            //转换成int
            idsArrInt[i] = Integer.parseInt(idsArr[i]);
        }
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        //设置一个批量删除判断标志,默认是正确的
        boolean flag = true;
        for (int id : idsArrInt) {
            if (!userDao.userDel(id)){
                //只要有错的，就将标志设置为false
                flag = false;
            }
        }
        if (flag) {
            //批量删除成功
            //存入提示信息
            request.setAttribute("userMessage","批量删除成功~");
            //调用用户列表函数,刷新页面
            userList(request,response);
        }else {
            //批量删除失败
            //存入提示信息
            request.setAttribute("userMessage","批量删除失败!");
            //调用用户列表函数,刷新页面
            userList(request,response);
        }
    }

    /**
     * 删除单个用户
     * @param request
     * @param response
     */
    private void userDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的用户id
        int userId = Integer.parseInt(request.getParameter("id"));
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        //根据用户id删除对应用户信息
        if (userDao.userDel(userId)){
            //删除成功
            //将提示信息存入request域中
            request.setAttribute("userMessage","删除成功~");
            //转发到用户列表页面,直接调用用户列表页面函数
            userList(request,response);
        }else {
            //将提示信息存入request域中
            request.setAttribute("userMessage","删除失败!");
            //转发到用户列表页面,直接调用用户列表页面函数
            userList(request,response);
        }
    }

    /**
     * 用户更新
     * @param request
     * @param response
     */
    private void userUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台提交的用户参数,并封装成对象
        //密码要用MD5加密
        String passWord = request.getParameter("passWord");
        User user = new User(
                Integer.parseInt(request.getParameter("userId")),
                SaltMD5Util.generateSaltPassword(passWord),
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address"),
                request.getParameter("enabled")
        );
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        if (userDao.userUpdate(user)){
            //修改成功
            //将提示信息存入request域中
            request.setAttribute("userMessage","用户信息修改成功~");
            //转发到用户列表页面,直接调用用户列表页面函数
            //这里我感觉转发到编辑页面也行,都可以
            userList(request,response);
        }else {
            //修改失败
            //将提示信息存入request域中
            request.setAttribute("userMessage","用户信息修改失败!");
            //转发到用户列表页面,直接调用用户列表页面函数
            //这里我感觉转发到编辑页面也行,都可以
            userList(request,response);
        }
    }

    /**
     * 根据id得到用户信息传给更新页面使用
     * @param request
     * @param response
     */
    private void userEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据前台参数id获取用户信息
        int userId = Integer.parseInt(request.getParameter("id"));
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserByUserId(userId);
        //将信息存入request域中
        request.setAttribute("userInfo",user);
        //转发到用户修改页面
        request.getRequestDispatcher(USERUPDATE_PATH).forward(request, response);
    }

    /**
     * 校验用户名是否可以使用
     * @param request
     * @param response
     */
    private void userAjaxUrlFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前台传递来的用户名是否存在
        //获取参数
        String username = request.getParameter("param");
        //获取键名
        String name = request.getParameter("name");
        //将用户名传入dao调用方法
        UserDao userDao = new UserDaoImpl();

        //创建Json对象
        JSONObject json = new JSONObject();
        if (username == null || "".equals(username)) {
            json.put("info", "other");
        } else {
            if (userDao.findUser(username)) {
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
     * 添加用户
     * @param request
     * @param response
     */
    private void userAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //密码封装时要用MD5加密
        String passWord = request.getParameter("passWord");
        //获取前台参数并封装成对象
        User user = new User(
                request.getParameter("userName"),
                SaltMD5Util.generateSaltPassword(passWord),
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address")
        );
        //设置用户使用状态,默认为y，表示开放状态
        user.setEnabled("y");
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        //存入数据库
        if (userDao.userAdd(user)){
            //添加成功
            //存入提示信息
            request.setAttribute("userMessage","用户添加成功~");
            //转发到用户列表展示页面,这里直接调用用户列表展示函数
            userList(request,response);
        }else {
            //添加失败
            //存入提示信息
            request.setAttribute("userMessage","用户添加失败!");
            //转发到用户列表展示页面,这里直接调用用户列表展示函数
            userList(request,response);
        }
    }

    /**
     * 用户详情页
     * @param request
     * @param response
     */
    private void userDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传来的参数用户id
        int userId = Integer.parseInt(request.getParameter("id"));
        //根据用户id查询对应用户信息
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findUserByUserId(userId);
        //把信息存入request域中
        request.setAttribute("userInfo",user);
        //转发到用户详情页面
        request.getRequestDispatcher(USERDETAIL_PATH).forward(request, response);
    }

    /**
     * 用户列表展示
     * @param request
     * @param response
     */
    private void userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置当前页面值
        int curPage = 1;
        //得到页面page参数
        String page = request.getParameter("page");
        if (page != null){
            //说明进行了跳转,把获取到的值赋给curPage
            curPage = Integer.parseInt(page);
        }
        //创建UserDao对象
        UserDao userDao = new UserDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage,MAX_PAGE_SIZE,userDao.allUserCount());
        //将需要的值存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("userList",userDao.userList(pageBean));
        //将数据转发到管理员列表页面，并跳转过去
        request.getRequestDispatcher(USERLIST_PATH).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
