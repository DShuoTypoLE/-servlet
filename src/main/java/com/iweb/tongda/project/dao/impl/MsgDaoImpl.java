package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Msg;
import com.iweb.tongda.project.dao.MsgDao;
import com.iweb.tongda.project.util.DbUtil;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-04 19:51
 */
public class MsgDaoImpl implements MsgDao {
    /**
     * 添加留言
     * @param msg
     */
    @Override
    public boolean msgAdd(Msg msg) {
        String sql = "insert into s_msg(contain,inputtime,inputperson) " +
                "values(?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                msg.getContain(),
                msg.getInputtime(),
                msg.getInputperson());
        return i > 0 ? true: false;
    }
}
