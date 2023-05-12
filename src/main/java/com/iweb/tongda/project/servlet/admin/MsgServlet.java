package com.iweb.tongda.project.servlet.admin;

import com.iweb.tongda.project.bean.Msg;
import com.iweb.tongda.project.bean.PageBean;
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
@WebServlet({"/jsp/admin/MsgServlet","/MsgServlet"})
public class MsgServlet extends HttpServlet {
    private static final int MAX_PAGE_SIZE = 8;
    private static final String MSG_LIST_PATH = "msgManage/msgList.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取action行为
        String action = request.getParameter("action");
        switch (action) {
            case "add":
                addMsg(request, response);
                break;
            case "list":
                MsgList(request, response);
                break;
            case "delete":
                MsgDelete(request, response);
                break;
        }
    }

    /**
     * 删除留言
     * @param request
     * @param response
     */
    private void MsgDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取前台的留言id
        int msgId = Integer.parseInt(request.getParameter("id"));
        //创建MsgDao对象
        MsgDao msgDao = new MsgDaoImpl();
        if (msgDao.deleteMsg(msgId)){
            //删除成功
            request.setAttribute("msgMessage","删除成功~");
            //调用列表函数
            MsgList(request, response);
        }else {
            //删除失败
            request.setAttribute("msgMessage","删除失败!");
            //调用列表函数
            MsgList(request, response);
        }
    }

    /**
     * 留言列表
     *
     * @param request
     * @param response
     */
    private void MsgList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置分页
        int curPage = 1;
        String page = request.getParameter("page");
        if (page != null) {
            curPage = Integer.parseInt(page);
        }
        //创建MsgDao对象
        MsgDao msgDao = new MsgDaoImpl();
        //创建分页对象
        PageBean pageBean = new PageBean(curPage, MAX_PAGE_SIZE, msgDao.allMsgCount());
        //将信息存入request域中
        request.setAttribute("pageBean",pageBean);
        request.setAttribute("msgList",msgDao.findMsgByPage(pageBean));
        //转发到留言列表界面
        request.getRequestDispatcher(MSG_LIST_PATH).forward(request,response);
    }

    /**
     * 添加留言
     *
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
        JSONObject json = new JSONObject();
        //设置响应编码
        response.setContentType("text/json;charset=utf-8");
        //msgDao调用添加方法,添加留言
        if (msgDao.msgAdd(msg)) {
            json.put("data","成功~");
            response.getWriter().write(json.toString());
            System.out.println("添加成功");
        }else{
            json.put("data","失败!");
            response.getWriter().write(json.toString());
            System.out.println("添加失败");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
