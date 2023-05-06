package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Admin;

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
}
