package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Msg;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-04 19:51
 */
public interface MsgDao {
    /**
     * 添加留言
     * @param msg
     */
    boolean msgAdd(Msg msg);
}
