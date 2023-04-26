package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.FlowDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 13:50
 */
public class FlowDaoImpl implements FlowDao {
    /**
     * 通过分类id查询鲜花数量
     *
     * @param catalogId
     * @return
     */
    @Override
    public long flowReadCount(int catalogId) {
        //定义sql
        String sql = "select count(1) as count from s_flow where catalogId = ?";

        //通过工具类查询
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, catalogId);

        //if (mapList.size() > 0) {
        //    return (int) mapList.get(0).get("count");
        //}else {
        //    return 0;
        //}
        //优化
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 随机返回鲜花数量
     *
     * @param i
     * @return
     */
    @Override
    public List<Flow> flowList(int i) {
        String sql = "select * from view_flow order by rand() limit ?";
        List<Flow> flowList = new ArrayList<Flow>();

        //把mapList中的所有鲜花封装成单个鲜花对象,然后放到list中
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, i);
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Flow flow = new Flow(map);
                flowList.add(flow);
            }
        }
        return flowList;
    }

    /**
     * 返回最新的鲜花列表
     *
     * @param i
     * @return
     */
    @Override
    public List<Flow> newFlowList(int i) {
        String sql = "select * from view_flow order by addTime desc limit ?";
        List<Flow> flowList = new ArrayList<Flow>();

        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, i);
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Flow flow = new Flow(map);
                flowList.add(flow);
            }
        }
        return flowList;
    }

    /**
     * 根据分页数据和分页id查询鲜花集合
     *
     * @param pageBean
     * @param catalogId
     * @return
     */
    @Override
    public List<Flow> typeFlowList(PageBean pageBean, int catalogId) {
        String sql = "select * from view_flow where catalogId = ? limit ?,?";
        //定义返回集合容器
        List<Flow> flowList = new ArrayList<Flow>();
        //limit 0,12  -> 12,12 -> 24,12
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, catalogId,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        //把查询的list中的map封装成鲜花对象
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Flow flow = new Flow(map);
                flowList.add(flow);
            }
        }
        return flowList;
    }

    /**
     * 所有鲜花返回数量
     *
     * @return
     */
    @Override
    public long allFlowList() {
        String sql = "select count(*) as count from s_flow";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 通过分页对象查询所有鲜花
     *
     * @param pageBean
     * @return
     */
    @Override
    public List<Flow> flowList(PageBean pageBean) {
        String sql = "select * from view_flow limit ?,?";
        List<Flow> flowList = new ArrayList<Flow>();
        List<Map<String, Object>> mapList =
                DbUtil.executeQuery(sql,
                        (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                        pageBean.getMaxSize());
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                Flow flow = new Flow(map);
                flowList.add(flow);
            }
        }
        return flowList;
    }

    @Override
    public Flow findFlowById(int flowId) {
        String sql = "select * from view_flow where flowId = ?";
        Flow flow = null;

        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,flowId);
        if (mapList.size() > 0) {
            flow = new Flow(mapList.get(0));
        }
        return flow;
    }
}
