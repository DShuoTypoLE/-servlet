package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 13:49
 */
public interface FlowDao {
    /**
     * 通过分类id查询鲜花数量
     * @param catalogId
     * @return
     */
    long flowReadCount(int catalogId);

    /**
     * 随机返回鲜花列表
     * @param i
     * @return
     */
    List<Flow> flowList(int i);

    /**
     * 返回最新的鲜花列表
     * @param i
     * @return
     */
    List<Flow> newFlowList(int i);

    /**
     * 根据分页数据和分页id查询鲜花集合
     * @param pageBean
     * @param catalogId
     * @return
     */
    List<Flow> typeFlowList(PageBean pageBean, int catalogId);

    /**
     * 所有鲜花返回数量
     * @return
     */
    long allFlowList();

    /**
     * 通过分页对象查询所有鲜花
     * @param pageBean
     * @return
     */
    List<Flow> flowList(PageBean pageBean);

    /**
     * 根据鲜花id查询单个鲜花
     * @param flowId
     * @return
     */
    Flow findFlowById(int flowId);
}
