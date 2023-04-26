package com.iweb.tongda.project.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {

    private int orderId;            //订单编号
    private String orderNum;		//订单号
    private int userId;             //用户编号
    private String orderDate;       //订单日期
    private double money;			//订单金额
    private int orderStatus;     	//订单状态
	private Integer chargerid; 		//配送员id
	private String chargername; //配送员姓名
	private String chargerphone;//配送员联系方式
	private String shpeople;   //收货人
	private String shphone; 	//收货人联系方式
	private String shaddress; 	//收货人地址


	public String getChargerphone() {
		return chargerphone;
	}

	public void setChargerphone(String chargerphone) {
		this.chargerphone = chargerphone;
	}

	private List<OrderItem> oItem=new ArrayList<>();
    private User user=new User();

    public Order() {
    }

    public Order(Map<String, Object> map) {
		this.setOrderId((int) map.get("orderId"));
		this.setOrderNum((String) map.get("orderNum"));
		this.setUserId((int) map.get("userId"));
		this.setOrderDate((String) map.get("orderDate"));
		this.setMoney((double) map.get("money"));
		this.setOrderStatus((int) map.get("orderStatus"));
		this.setChargerid((int)map.get("chargerid"));
		this.setChargername((String)map.get("chargername"));
		this.setChargerphone((String)map.get("chargerphone"));
		this.setShpeople((String)map.get("shpeople"));
		this.setShaddress((String)map.get("shaddress"));
		this.setShphone((String)map.get("shphone"));
	}

	public void setChargerid(Integer chargerid) {
		this.chargerid = chargerid;
	}

	public String getShpeople() {
		return shpeople;
	}

	public void setShpeople(String shpeople) {
		this.shpeople = shpeople;
	}

	public String getShphone() {
		return shphone;
	}

	public void setShphone(String shphone) {
		this.shphone = shphone;
	}

	public String getShaddress() {
		return shaddress;
	}

	public void setShaddress(String shaddress) {
		this.shaddress = shaddress;
	}

	public int getChargerid() {
		return chargerid;
	}

	public void setChargerid(int chargerid) {
		this.chargerid = chargerid;
	}

	public String getChargername() {
		return chargername;
	}

	public void setChargername(String chargername) {
		this.chargername = chargername;
	}
	public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    
    public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}



	public List<OrderItem> getoItem() {
		return oItem;
	}

	public void setoItem(List<OrderItem> oItem) {
		this.oItem = oItem;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
    
}
