package com.iweb.tongda.project.bean;

import com.iweb.tongda.project.util.MathUtils;

public class CartItem {
	private Flow flow;
	private int quantity;//数量
	private double subtotal;//小计
	
	public CartItem() {}
	
	
	
	public CartItem(Flow flow, int quantity) {
		super();
		this.setFlow(flow);
		this.setQuantity(quantity);
	}

	public Flow getFlow() {
		return flow;
	}
	public void setFlow(Flow flow) {
		this.flow = flow;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.subtotal = MathUtils.getTwoDouble(quantity*flow.getPrice());
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	

	@Override
	public String toString() {
		return "CartItem [flow=" + flow + ", quantity=" + quantity + ", subtotal=" + subtotal + "]";
	}
	
	
	
}
