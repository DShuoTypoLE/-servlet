package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.bean.User;
import com.iweb.tongda.project.dao.UserDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
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
     *
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

    /**
     * 根据userId查询user
     *
     * @param userId
     * @return
     */
    @Override
    public User findUserByUserId(int userId) {
        String sql = "select * from s_user where userId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, userId);
        User user = null;
        if (mapList.size() > 0) {
            user = new User(mapList.get(0));
        }
        return user;
    }

    /**
     * 获取所有用户总数量
     *
     * @return
     */
    @Override
    public long allUserCount() {
        String sql = "select count(*) as count from s_user";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页对象查询用户信息
     *
     * @param pageBean
     * @return
     */
    @Override
    public List<User> userList(PageBean pageBean) {
        String sql = "select * from s_user limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<User> list = new ArrayList<>();
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                User user = new User(map);
                list.add(user);
            }
        }
        return list;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean userUpdate(User user) {
        String sql = "update s_user set userPassWord = ?," +
                "name = ?," +
                "sex = ?," +
                "age = ?," +
                "tell = ?," +
                "address = ?," +
                "enabled = ? " +
                "where userId = ?";
        int i = DbUtil.executeUpdate(sql,
                user.getUserPassWord(),
                user.getName(),
                user.getSex(),
                user.getAge(),
                user.getTell(),
                user.getAddress(),
                user.getEnabled(),
                user.getUserId());
        return i > 0 ? true : false;
    }

    /**
     * 删除单个用户
     *
     * @param userId
     * @return
     */
    @Override
    public boolean userDel(int userId) {
        String sql = "delete from s_user where userId = ?";
        int i = DbUtil.executeUpdate(sql, userId);
        return i > 0 ? true : false;
    }

    /**
     * 根据所输入用户名模糊查询总数据量
     *
     * @param username
     * @return
     */
    @Override
    public long readUserCountByLike(String username) {
        String sql = "select count(*) as count from s_user where userName like '%" + username + "%'";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, username);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页对象和用户名进行模糊查询
     * @param pageBean
     * @param username
     * @return
     */
    @Override
    public List<User> findUserByLike(PageBean pageBean, String username) {
        String sql = "select * from s_user where userName like '%"+username+"%' " +
                "limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<User> list = new ArrayList<>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                User user = new User(map);
                list.add(user);
            }
        }
        return list;
    }
}
