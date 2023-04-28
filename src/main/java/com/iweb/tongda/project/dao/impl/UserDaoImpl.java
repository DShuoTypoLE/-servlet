package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.User;
import com.iweb.tongda.project.dao.UserDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 9:04
 */
public class UserDaoImpl implements UserDao {
    /**
     * 验证用户名是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean findUser(String username) {
        String sql = "select * from s_user where userName = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, username);
        return mapList.size() > 0 ? true : false;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean userAdd(User user) {
        String sql = "insert into s_user(userName,userPassWord,name,sex,age,tell,address,enabled)" +
                " values(?,?,?,?,?,?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                user.getUserName(),
                user.getUserPassWord(),
                user.getName(),
                user.getSex(),
                user.getAge(),
                user.getTell(),
                user.getAddress(),
                user.getEnabled());
        return i > 0 ? true : false;
    }

    /**
     * 根据用户名返回User对象
     * @param user
     * @return
     */
    @Override
    public User findUser(User user) {
        String sql = "select * from s_user where userName = ?";

        //这里不能直接new User()防止用户名错误不走下面的if语句
        User user1 = null;
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, user.getUserName());
        if (mapList.size() > 0) {
            user1 = new User(mapList.get(0));
        }
        return user1;
    }
}
