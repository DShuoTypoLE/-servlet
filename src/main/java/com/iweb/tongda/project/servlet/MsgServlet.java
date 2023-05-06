package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.bean.Msg;
import com.iweb.tongda.project.bean.User;
import com.iweb.tongda.project.dao.MsgDao;
import com.iweb.tongda.project.dao.impl.MsgDaoImpl;
import com.iweb.tongda.project.util.DateUtil;
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
 * @Date 2023-05-04 19:32
 */
@WebServlet("/jsp/admin/MsgServlet")
public class MsgServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取action行为
        String action = request.getParameter("action");
        System.out.println("我来啦");
        switch (action) {
            case "add":
                addMsg(request,response);
                break;
        }
    }

    /**
     * 添加留言
     * @param request
     * @param response
     */
    private void addMsg(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取留言内容
        String contain = request.getParameter("contain");
        //留言时间
        String inputTime = DateUtil.show();
        //获取留言客户
        User user = (User) request.getSession().getAttribute("landing");
        //获取当前用户用户名userName
        String userName = user.getUserName();
        //将上述信息封装成留言对象
        Msg msg = new Msg(contain, inputTime, userName);
        //创建MsgDao对象
        MsgDao msgDao = new MsgDaoImpl();
        //msgDao调用添加方法,添加留言
        if (msgDao.msgAdd(msg)){
            response.getWriter().write("true");
            System.out.println("添加成功");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
