package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Msg;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.MsgDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     * 留言数量
     * @return
     */
    @Override
    public long allMsgCount() {
        String sql = "select count(1) as count from s_msg";
        List<Map<String, Object>> maoList = DbUtil.executeQuery(sql);

        return maoList.size() > 0 ? (long) maoList.get(0).get("count") :0;
    }

    /**
     * 根据分页对象查找每页留言数据
     * @param pageBean
     * @return
     */
    @Override
    public List<Msg> findMsgByPage(PageBean pageBean) {
        String sql = "select * from s_msg limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Msg> list = new ArrayList<Msg>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                Msg msg = new Msg(map);
                list.add(msg);
            }
        }
        return list;
    }

    /**
     * 删除留言
     * @param msgId
     * @return
     */
    @Override
    public boolean deleteMsg(int msgId) {
        String sql = "delete from s_msg where id = ?";
        int i = DbUtil.executeUpdate(sql, msgId);
        return i > 0 ? true: false;
    }
}
