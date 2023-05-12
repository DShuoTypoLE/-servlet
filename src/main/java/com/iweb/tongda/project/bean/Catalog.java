package com.iweb.tongda.project.bean;

import java.util.Map;

public class Catalog {

    private int catalogId;          //图片分类编号

    private String catalogName;     //图片分类名称

    private long catalogSize;    //分类内容数量
    
    public Catalog() {
    }

    public Catalog(int catalogId, String catalogName) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
    }

    public Catalog(Map<String,Object> map) {
        this.catalogId= (int) map.get("catalogId");
        this.catalogName= (String) map.get("catalogName");
    }
    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public long getCatalogSize() {
        return catalogSize;
    }

    public void setCatalogSize(long catalogSize) {
        this.catalogSize = catalogSize;
    }

    @Override
	public String toString() {
		return "Catalog [catalogId=" + catalogId + ", catalogName="
				+ catalogName + "]";
	}
    
}
