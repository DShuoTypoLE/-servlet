package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.User;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 9:04
 */
public interface UserDao {
    /**
     * 验证用户名是否存在,根据用户名查找用户
     * @param username
     * @return
     */
    boolean findUser(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    boolean userAdd(User user);

    /**
     * 根据用户名返回User对象
     * @param user
     * @return
     */
    User findUser(User user);

    /**
     * 根据userId查询user
     * @param userId
     * @return
     */
    User findUserByUserId(int userId);
}
