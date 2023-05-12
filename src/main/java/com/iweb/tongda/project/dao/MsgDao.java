package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Msg;
import com.iweb.tongda.project.bean.PageBean;

import java.util.List;

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

    /**
     * 留言数量
     * @return
     */
    long allMsgCount();

    /**
     * 根据分页对象查找每页留言数据
     * @param pageBean
     * @return
     */
    List<Msg> findMsgByPage(PageBean pageBean);

    /**
     * 删除留言
     * @param msgId
     * @return
     */
    boolean deleteMsg(int msgId);
}
