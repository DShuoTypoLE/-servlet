package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Catalog;

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
}
