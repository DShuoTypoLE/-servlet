package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Catalog;
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
        /**
         * java.lang.Long cannot be cast to java.lang.Integer 注意是类型
         * 关于上面报错问题的解决方案 详细参见笔记
         */
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
        //select * from table_name limit [offset，] rows
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

    /**
     * 根据鲜花id查询单个鲜花
     *
     * @param flowId
     * @return
     */
    @Override
    public Flow findFlowById(int flowId) {
        String sql = "select * from view_flow where flowId = ?";
        //这一步很有深意,不能直接new出来
        Flow flow = null;

        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, flowId);
        if (mapList.size() > 0) {
            flow = new Flow(mapList.get(0));
        }
        return flow;
    }

    /**
     * 根据鲜花名判断鲜花是否存在
     *
     * @param flowname
     * @return
     */
    @Override
    public boolean findFlowByFlowname(String flowname) {
        String sql = "select * from s_flow where flowName = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, flowname);
        return mapList.size() > 0 ? true : false;
    }

    /**
     * 添加花卉
     *
     * @param flow
     * @return
     */
    @Override
    public boolean addFlow(Flow flow) {
        String sql = "insert into s_flow(catalogId," +
                "flowName," +
                "price," +
                "description," +
                "imgId," +
                "addTime," +
                "keywords) values(?,?,?,?,?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                flow.getCatalogId(),
                flow.getFlowName(),
                flow.getPrice(),
                flow.getDescription(),
                flow.getImgId(),
                flow.getAddTime(),
                flow.getKeywords());
        return i > 0 ? true : false;
    }

    /**
     * 根据鲜花id查询当前鲜花分类信息
     * @param flowId
     * @return
     */
    @Override
    public Catalog getCatalogByFlowId(int flowId) {
        String sql = "select catalogId,catalogName from view_flow where flowId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, flowId);
        Catalog catalog = null;
        if (mapList.size() > 0){
            catalog = new Catalog(mapList.get(0));
        }
        return catalog;
    }

    /**
     * 根据鲜花id修改鲜花表中的图片id
     * @param flowId
     * @param imgId
     * @return
     */
    @Override
    public boolean updateImgIdAtFlow(int flowId,int imgId) {
        String sql = "update s_flow set imgId = ? where flowID = ?";
        int i = DbUtil.executeUpdate(sql, imgId, flowId);
        return i > 0 ? true: false;
    }

    /**
     * 修改鲜花信息
     * @param flow
     * @return
     */
    @Override
    public boolean updateFlow(Flow flow) {
        String sql = "update s_flow set price = ?," +
                "description = ?," +
                "catalogId = ?," +
                "keywords = ? where flowID = ?";
        int i = DbUtil.executeUpdate(sql,
                flow.getPrice(),
                flow.getDescription(),
                flow.getCatalogId(),
                flow.getKeywords(),
                flow.getFlowId());
        return i > 0 ? true: false;
    }

    /**
     * 根据鲜花id删除单个鲜花
     * @param flowId
     * @return
     */
    @Override
    public boolean deleteFlowById(int flowId) {
        String sql = "delete from s_flow where flowID = ?";
        int i = DbUtil.executeUpdate(sql, flowId);
        return i > 0 ? true: false;
    }

    /**
     * 根据鲜花名称模糊查询数据个数
     * @param flowName
     * @return
     */
    @Override
    public long readFlowCountByLike(String flowName) {
        String sql = "select count(*) as count from s_flow where flowName like '%"+flowName+"%'";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据鲜花名称模糊查询鲜花列表信息
     * @param pageBean
     * @param flowName
     * @return
     */
    @Override
    public List<Flow> findFlowByLike(PageBean pageBean, String flowName) {
        String sql = "select * from view_flow where flowName like '%"+flowName+"%' limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Flow> list = new ArrayList<Flow>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                Flow flow = new Flow(map);
                list.add(flow);
            }
        }
        return list;
    }
}
