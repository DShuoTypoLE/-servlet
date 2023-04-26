package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Catalog;
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
}
