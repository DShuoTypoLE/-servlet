package com.iweb.tongda.project.servlet;

import com.iweb.tongda.project.util.ImageCode;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 10:20
 */
@WebServlet("/CodeServlet")
public class CodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断前台传递过来的action参数，点击登录页面的时候
        //页面携带的是 src="CodeServlet?action=code" 执行获取验证码方法
        //或者点击切换验证码按钮："reCode()"，会重新获取验证码
        String action=request.getParameter("action");
        if("code".equals(action)) {
            getCode(request,response);
        }
        //ckCode是验证验证码是否正确，如果是输入验证码之后，就走这个方法，判断验证码对不对
        //请求路径为：ajaxurl:"CodeServlet?action=ckCode",
        if("ckCode".equals(action)) {
            ckCode(request,response);
        }
    }

    private void ckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code=request.getParameter("param");
        String ck_code=(String) request.getSession().getAttribute("checkCode");
        JSONObject json = new JSONObject();
        //比较验证码并返回信息
        if (ck_code.equals(code)) {
            json.put("info", "验证码正确");
            json.put("status", "y");
        } else {
            json.put("info", "验证码输入不正确");
            json.put("status", "n");
        }
        response.getWriter().write(json.toString());
    }

    private void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置浏览器不要缓存此图片
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ServletOutputStream outputStream = response.getOutputStream();
        String rands= ImageCode.getImageCode(70, 30, outputStream);
        //将生成的随机四个字符保存在session范围checkCode属性
        request.getSession().setAttribute("checkCode", rands);

        outputStream.close();

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
