package com.iweb.tongda.project.bean;

import java.util.Map;

public class OrderItem {

    private int itemId;           //订单项编号
    private int flowId;          //鲜花编号
    private int orderId;       //订单编号
    private int quantity;      //数量
    
    private Flow flow;
    public OrderItem() {
    }

   
    public OrderItem(Map<String, Object> map) {
		this.setOrderId((int) map.get("orderId"));
		this.setFlowId((int) map.get("flowId"));
		this.setItemId((int) map.get("itemId"));
		this.setQuantity((int) map.get("quantity"));
	}


	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public int getFlowId() {
		return flowId;
	}


	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Flow getFlow() {
		return flow;
	}


	public void setFlow(Flow flow) {
		this.flow = flow;
	}


    
}
