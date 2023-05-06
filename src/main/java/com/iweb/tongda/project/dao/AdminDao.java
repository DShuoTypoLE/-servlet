package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Admin;
import com.iweb.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-04 21:47
 */
public interface AdminDao {
    /**
     * 判断管理员是否登录
     * @param admin
     * @return
     */
    boolean adminLogin(Admin admin);

    /**
     * 查询管理员数量
     * @return
     */
    long allAdminCount();

    /**
     * 根据分页查询管理员列表
     * @param pageBean
     * @return
     */
    List<Admin> adminList(PageBean pageBean);

    /**
     * 根据管理员id查询管理员信息
     * @param adminId
     * @return
     */
    Admin findAdminById(int adminId);

    /**
     * 根据管理员id修改管理员信息
     * @param admin
     * @return
     */
    boolean updateAdminById(Admin admin);

    /**
     * 根据登录信息里面的用户名查询对应的管理员id
     * @param userName
     * @return
     */
    int findAdminIdByUsername(String userName);

    /**
     * 添加管理员
     * @param admin
     * @return
     */
    boolean addAdmin(Admin admin);

    /**
     * 根据用户名查找管理员是否存在
     * @param username
     * @return
     */
    boolean findAdminByUsername(String username);

    /**
     * 根据管理员id删除对应管理员信息
     * @param adminId
     * @return
     */
    boolean delAdmin(int adminId);

    /**
     * 根据多个管理员id删除对应管理员信息
     * @param idsArrInt
     * @return
     */
    boolean batDelAdmin(int[] idsArrInt);

}
