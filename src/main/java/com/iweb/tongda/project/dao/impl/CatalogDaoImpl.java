package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Catalog;
import com.iweb.tongda.project.bean.PageBean;
import com.iweb.tongda.project.dao.CatalogDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 11:59
 */
public class CatalogDaoImpl implements CatalogDao {
    /**
     * 获取分类列表
     * @return
     */
    @Override
    public List<Catalog> getCatalog() {
        String sql = "select * from s_catalog";
        List<Catalog> list = new ArrayList<Catalog>();

        //调用工具类的查询方法
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);

        //判断List内容
        //遍历mapList的所有分类,遍历出来后封装成一个Catalog对象,再放到返回的list中
        if (mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                //把map传入构造方法构建对象
                Catalog catalog = new Catalog(map);
                list.add(catalog);
            }
        }
        return list;
    }

    /**
     * 查询分类数量
     * @return
     */
    @Override
    public long allCatalogCount() {
        String sql = "select count(1) as count from s_catalog";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        return mapList.size() > 0 ? (long) mapList.get(0).get("count") :0;
    }

    /**
     * 根据分页查询每页显示的分类信息
     * @param pageBean
     * @return
     */
    @Override
    public List<Catalog> getCatalogByPage(PageBean pageBean) {
        String sql = "select * from s_catalog limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Catalog> list = new ArrayList<>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                Catalog catalog = new Catalog(map);
                list.add(catalog);
            }
        }
        return list;
    }

    /**
     * 根据分类名添加分类
     * @param catalogName
     * @return
     */
    @Override
    public boolean addCatalog(String catalogName) {
        String sql = "insert into s_catalog(catalogName) values(?)";
        int i = DbUtil.executeUpdate(sql, catalogName);
        return i > 0 ? true: false;
    }

    /**
     * 判断分类名是否可用
     * @param catalogName
     * @return
     */
    @Override
    public boolean findCatalog(String catalogName) {
        String sql = "select * from s_catalog where catalogName = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, catalogName);
        return mapList.size() > 0 ? true: false;
    }

    /**
     * 根据分类id查询分类信息
     * @param catalogId
     * @return
     */
    @Override
    public Catalog getCatalogById(int catalogId) {
        String sql = "select * from s_catalog where catalogId = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, catalogId);
        Catalog catalog = null;
        if (mapList.size() > 0) {
            catalog = new Catalog(mapList.get(0));
        }
        return catalog;
    }

    /**
     * 判单是否修改成功
     * @param catalog
     * @return
     */
    @Override
    public boolean updateCatalog(Catalog catalog) {
        String sql = "update s_catalog set catalogName = ? where catalogId = ?";
        int i = DbUtil.executeUpdate(sql, catalog.getCatalogName(), catalog.getCatalogId());
        return i > 0 ? true: false;
    }

    /**
     * 根据分类id删除单个
     * @param catalogId
     * @return
     */
    @Override
    public boolean delCatalog(int catalogId) {
        String sql = "delete from s_catalog where catalogId = ?";
        int i = DbUtil.executeUpdate(sql, catalogId);
        return i > 0 ? true: false;
    }
}
