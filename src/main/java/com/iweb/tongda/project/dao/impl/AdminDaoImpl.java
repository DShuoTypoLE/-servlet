package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.AdminDao;
import com.iweb.tongda.project.util.DateUtil;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
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
     *
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

        if (mapList.size() > 0) {
            //登陆成功
            flag = true;
            //设置管理员姓名
            admin.setName((String) mapList.get(0).get("name"));
            //更新登陆时间
            DbUtil.executeUpdate(sql2, DateUtil.getTimestamp(), mapList.get(0).get("id"));
        }
        return flag;
    }

    /**
     * 查询管理员数量
     *
     * @return
     */
    @Override
    public long allAdminCount() {
        String sql = "select count(*) as count from s_admin";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页查询管理员列表
     * @param pageBean
     * @return
     */
    @Override
    public List<Admin> adminList(PageBean pageBean) {
        String sql = "select * from s_admin limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Admin> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Admin admin = new Admin(map);
            list.add(admin);
        }
        return list;
    }

    /**
     * 根据管理员id查询管理员信息
     * @param adminId
     * @return
     */
    @Override
    public Admin findAdminById(int adminId) {
        String sql = "select * from s_admin where id = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, adminId);
        Admin admin = null;
        if (mapList.size() > 0){
            admin = new Admin(mapList.get(0));
        }
        return admin;
    }

    /**
     * 根据管理员id修改管理员信息
     * @param admin
     * @return
     */
    @Override
    public boolean updateAdminById(Admin admin) {
        String sql = "update s_admin set passWord = ?,name = ? where id = ?";
        int i = DbUtil.executeUpdate(sql,
                admin.getPassWord(),
                admin.getName(),
                admin.getId());
        return i > 0 ? true: false;
    }

    /**
     * 根据登录信息里面的用户名查询对应的管理员id
     * @param userName
     * @return
     */
    @Override
    public int findAdminIdByUsername(String userName) {
        String sql = "select id from s_admin where userName = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, userName);
        //这里-1没有什么含义,主要是区分没找到的情况,但是肯定会找到，所以没啥用~
        return mapList.size() > 0 ? (int) mapList.get(0).get("id") : -1;
    }

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    @Override
    public boolean addAdmin(Admin admin) {
        String sql = "insert into s_admin(userName,passWord,name) values(?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                admin.getUserName(),
                admin.getPassWord(),
                admin.getName());
        return i > 0 ? true: false;
    }

    /**
     * 根据用户名查找管理员是否存在
     * @param username
     * @return
     */
    @Override
    public boolean findAdminByUsername(String username) {
        String sql = "select * from s_admin where userName = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, username);
        return mapList.size() > 0 ? true: false;
    }

    /**
     * 根据管理员id删除对应管理员信息
     * @param adminId
     * @return
     */
    @Override
    public boolean delAdmin(int adminId) {
        String sql = "delete from s_admin where id = ?";
        int i = DbUtil.executeUpdate(sql, adminId);
        return i > 0 ? true: false;
    }

    /**
     * 根据多个管理员id删除对应管理员信息
     * @param idsArrInt
     * @return
     */
    @Override
    public boolean batDelAdmin(int[] idsArrInt) {
        //设置批量删除标志
        boolean flag = true;
        for (int i = 0; i < idsArrInt.length; i++) {
            //调用上面删除单个函数,若有至少一个删除失败，
            //则把批量删除标志换成false,即删除失败
            if (!delAdmin(idsArrInt[i])){
                flag = false;
            }
        }
        return flag;
    }
}
