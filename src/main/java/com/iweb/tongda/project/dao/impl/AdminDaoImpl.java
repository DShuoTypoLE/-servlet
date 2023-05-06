package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.dao.AdminDao;
import com.iweb.tongda.project.util.DateUtil;
import com.iweb.tongda.project.util.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-04 21:47
 */
public class AdminDaoImpl implements AdminDao {
    /**
     * 判断管理员是否登录
     * @param admin
     * @return
     */
    @Override
    public boolean adminLogin(Admin admin) {
        boolean flag = false;
        //判断是否登录成功,如果成功,就把最后登陆时间修改一下
        String sql1 = "select * from s_admin where userName = ? and passWord = ?";
        String sql2 = "update s_admin set lastLoginTime = ? where id = ?";

        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql1, admin.getUserName(), admin.getPassWord());

        if (mapList.size() > 0){
            //登陆成功
            flag = true;
            //设置管理员姓名
            admin.setName((String) mapList.get(0).get("name"));
            //更新登陆时间
            DbUtil.executeUpdate(sql2, DateUtil.getTimestamp(),mapList.get(0).get("id"));
        }
        return flag;
    }
}
