package com.iweb.tongda.project.bean;

import java.util.Date;
import java.util.Map;

public class Flow {

	private int flowId; // 鲜花编号
	private String flowName; //  鲜花名称
	private double price; // 价格
	private String description; // 描述信息
	private int catalogId; //鲜花分类id
	private int imgId; // 图片id
	private Date addTime;//上架时间
	private String keywords;//关键词搜索设置

	private Catalog catalog = new Catalog();
	private UpLoadImg upLoadImg = new UpLoadImg();

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}



	public Flow() {
	}

	public Flow(Map<String, Object> map) {
		this.flowId = (int) map.get("flowId");
		this.flowName = (String) map.get("flowName");
		this.price = (double) map.get("price");
		this.description = (String) map.get("description");
		this.addTime=(Date) map.get("addTime");
		this.catalog = new Catalog(map);
		this.upLoadImg = new UpLoadImg(map);
		this.keywords = (String) map.get("keywords");
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public UpLoadImg getUpLoadImg() {
		return upLoadImg;
	}

	public void setUpLoadImg(UpLoadImg upLoadImg) {
		this.upLoadImg = upLoadImg;
	}

	@Override
	public String toString() {
		return "Flow [flowId=" + flowId + ", flowName=" + flowName + ", price=" + price + ", description=" + description
				+ ", catalogId=" + catalogId + ", imgId=" + imgId + ", addTime=" + addTime + ", catalog=" + catalog
				+ ", upLoadImg=" + upLoadImg + "]";
	}

	

	



}
