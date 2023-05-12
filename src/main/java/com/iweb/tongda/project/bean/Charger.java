package com.iweb.tongda.project.bean;

import java.util.Map;

//配送员
public class Charger {

    private Integer id;//主键
    private String name;//配送员姓名
    private String phone;//配送员手机号
    private String no;//配送员编号

    public Charger() {
    }

    public Charger(Integer id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Charger(String name, String phone, String no) {
        this.name = name;
        this.phone = phone;
        this.no = no;
    }

    public Charger(Map<String,Object> map) {
        this.id= (int) map.get("id");
        this.name= (String) map.get("name");
        this.phone= (String) map.get("phone");
        this.no= (String) map.get("no");
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

