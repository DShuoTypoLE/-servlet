package com.iweb.tongda.project.servlet;

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

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 8:47
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final String LOGIN_PATH = "jsp/flow/login.jsp";
    private static final String REG_PATH = "jsp/flow/reg.jsp";
    private static final String INDEX_PATH = "jsp/flow/index.jsp";
    //session中用户的标识
    private static final String LANDING = "landing";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断前台传递过来的action值
        String action = request.getParameter("action");
        switch (action) {
            case "reg":
                //如果是注册页面过来的,就提交注册
                register(request, response);
                break;
            case "login":
                login(request, response);
                break;
            case "ajaxFind":
                ajaxFindUser(request, response);
                break;
            case "off":
                logout(request,response);
                break;
            case "landstatus":
                islogin(request,response);
                break;
            case "ajlogin":
                modelLogin(request,response);
                break;
        }
    }

    /**
     * 模态框登录
     * @param request
     * @param response
     */
    private void modelLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取前台传递过来的用户名和密码
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        //构建对象
        User user = new User(userName, passWord);
        //通过userDao查询用户信息,返回整个User对象
        UserDao userDao = new UserDaoImpl();
        User user1 = userDao.findUser(user);

        JSONObject json = new JSONObject();

        //首先判断,从数据库查询的User存不存在,不存在,则用户名不对或者不存在
        if (user1 != null) {
            //用户存在,判断密码对不对
            //首先将数据库返回的密码解密
            if (!SaltMD5Util.verifySaltPassword(passWord, user1.getUserPassWord())) {
                //如果密码不同
                json.put("info","用户密码错误");
            } else {
                //用户名密码都正确
                //判断用户是否可以登录,是否锁定
                if ("y".equals(user1.getEnabled())) {
                    //将登陆的用户信息放入session
                    request.getSession().setAttribute(LANDING,user1);
                    json.put("status","y");
                }else {
                    json.put("info","用户被禁用,请联系管理员~");
                }
            }
        } else {
            //不存在
            json.put("info","用户名错误~");
        }
        response.getWriter().write(json.toString());
    }

    /**
     * 点击结算后判断是否登录，然后判断是否要弹出登录界面
     * @param request
     * @param response
     */
    private void islogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        User user = (User) request.getSession().getAttribute(LANDING);
        //如果有用户表示登录状态
        if (user != null){
            json.put("status","y");
        }
        response.getWriter().write(json.toString());
    }

    /**
     * 注销
     * @param request
     * @param response
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取到session中的User消息,然后remove就可以了
        User user = (User) request.getSession().getAttribute(LANDING);
        if (user != null) {
            request.getSession().removeAttribute(LANDING);
            response.sendRedirect(INDEX_PATH);
        }
    }

    /**
     * 登录
     *
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台传递过来的用户名和密码
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        //构建对象
        User user = new User(userName, passWord);
        //通过userDao查询用户信息,返回整个User对象
        UserDao userDao = new UserDaoImpl();
        User user1 = userDao.findUser(user);

        //首先判断,从数据库查询的User存不存在,不存在,则用户名不对或者不存在
        if (user1 != null) {
            //用户存在,判断密码对不对
            //首先将数据库返回的密码解密
            if (!SaltMD5Util.verifySaltPassword(passWord, user1.getUserPassWord())) {
                //如果密码不同
                request.setAttribute("infoList", "用户密码错误!");
                //转发回登陆页面
                request.getRequestDispatcher(LOGIN_PATH).forward(request,response);
            } else {
                //用户名密码都正确
                //判断用户是否可以登录,是否锁定
                if ("y".equals(user1.getEnabled())) {
                    //将登陆的用户信息放入session
                    request.getSession().setAttribute(LANDING,user1);
                    //重定向到首页
                    response.sendRedirect(INDEX_PATH);
                }else {
                    request.setAttribute("infoList","用户被锁定,请联系管理员!");
                    //转发到登陆页面
                    request.getRequestDispatcher(LOGIN_PATH).forward(request,response);
                }
            }
        } else {
            //不存在
            request.setAttribute("infoList","用户名错误!");
            //转发到登陆页面
            request.getRequestDispatcher(LOGIN_PATH).forward(request,response);
        }
    }

    /**
     * 验证用户名
     *
     * @param request
     * @param response
     */
    private void ajaxFindUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前台传递来的用户名是否存在
        String username = request.getParameter("username");
        //将用户名传入dao调用方法
        UserDao userDao = new UserDaoImpl();

        //创建Json对象
        JSONObject json = new JSONObject();
        if (username == null || username == "") {
            json.put("info", "other");
        } else {
            if (userDao.findUser(username)) {
                json.put("info", "true");
            } else {
                json.put("info", "false");
            }
        }
        response.getWriter().write(json.toString());
    }

    /**
     * 注册方法
     *
     * @param request
     * @param response
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //先在外面进行密码加密
        String passWord = request.getParameter("passWord");
        String newPassword = SaltMD5Util.generateSaltPassword(passWord);
        //获取用户的数据,并封装成一个User
        User user = new User(
                request.getParameter("userName"),
                newPassword,
                request.getParameter("name"),
                request.getParameter("sex"),
                Integer.parseInt(request.getParameter("age")),
                request.getParameter("tell"),
                request.getParameter("address")
        );
        //设置用户状态,默认是启用的
        user.setEnabled("y");
        //创建UserDao对象,创建添加方法
        UserDao userDao = new UserDaoImpl();
        if (userDao.userAdd(user)) {
            //注册成功
            request.setAttribute("infoList", "注册成功,请登录~");
            request.getRequestDispatcher(LOGIN_PATH).forward(request, response);
        } else {
            //注册失败
            request.setAttribute("infoList", "注册失败,请重新注册!");
            request.getRequestDispatcher(REG_PATH).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
