package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.bean.User;

import java.util.List;

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

    /**
     * 获取所有用户总数量
     * @return
     */
    long allUserCount();

    /**
     * 根据分页对象查询用户信息
     * @param pageBean
     * @return
     */
    List<User> userList(PageBean pageBean);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean userUpdate(User user);

    /**
     * 删除单个用户
     * @param userId
     * @return
     */
    boolean userDel(int userId);

    /**
     * 根据所输入用户名模糊查询总数据量
     * @param username
     * @return
     */
    long readUserCountByLike(String username);

    /**
     * 根据分页对象和用户名进行模糊查询
     * @param pageBean
     * @param username
     * @return
     */
    List<User> findUserByLike(PageBean pageBean, String username);
}
