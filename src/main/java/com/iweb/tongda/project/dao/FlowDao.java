package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.bean.Flow;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.bean.UpLoadImg;

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

    /**
     * 根据鲜花名判断鲜花是否存在
     * @param flowname
     * @return
     */
    boolean findFlowByFlowname(String flowname);

    /**
     * 添加花卉
     * @param flow
     * @return
     */
    boolean addFlow(Flow flow);

    /**
     * 根据鲜花id查询当前鲜花分类信息
     * @param flowId
     * @return
     */
    Catalog getCatalogByFlowId(int flowId);

    /**
     * 根据鲜花id修改鲜花表中的图片id
     * @param flowId
     * @param imgId
     * @return
     */
    boolean updateImgIdAtFlow(int flowId,int imgId);

    /**
     * 修改鲜花信息
     * @param flow
     * @return
     */
    boolean updateFlow(Flow flow);

    /**
     * 根据鲜花id删除单个鲜花
     * @param flowId
     * @return
     */
    boolean deleteFlowById(int flowId);

    /**
     * 根据鲜花名称模糊查询数据个数
     * @param flowName
     * @return
     */
    long readFlowCountByLike(String flowName);

    /**
     * 根据鲜花名称模糊查询鲜花列表信息
     * @param pageBean
     * @param flowName
     * @return
     */
    List<Flow> findFlowByLike(PageBean pageBean, String flowName);

    /**
     * 根据图片id得到图片信息
     * @param imgId
     * @return
     */
    UpLoadImg getUpLoadImg(int imgId);
}
