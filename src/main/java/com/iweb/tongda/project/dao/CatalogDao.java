package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.bean.PageBean;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 11:59
 */
public interface CatalogDao {
    /**
     * 获取分类列表
     * @return
     */
    List<Catalog> getCatalog();

    /**
     * 查询分类数量
     * @return
     */
    long allCatalogCount();

    /**
     * 根据分页查询每页显示的分类信息
     * @param pageBean
     * @return
     */
    List<Catalog> getCatalogByPage(PageBean pageBean);

    /**
     * 根据分类名添加分类
     * @param catalogName
     * @return
     */
    boolean addCatalog(String catalogName);

    /**
     * 判断分类名是否可用
     * @param catalogName
     * @return
     */
    boolean findCatalog(String catalogName);

    /**
     * 根据分类id查询分类信息
     * @param catalogId
     * @return
     */
    Catalog getCatalogById(int catalogId);

    /**
     * 判单是否修改成功
     * @param catalog
     * @return
     */
    boolean updateCatalog(Catalog catalog);

    /**
     * 根据分类id删除单个
     * @param catalogId
     * @return
     */
    boolean delCatalog(int catalogId);
}
