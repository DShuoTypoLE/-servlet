package com.iweb.tongda.project.bean;

import java.util.Map;

//留言
public class Msg {
    private Integer id;//主键
    private String contain;//内容
    private String inputtime;//录入时间
    private String inputperson;//录入人

    public Msg() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime;
    }

    public String getInputperson() {
        return inputperson;
    }

    public void setInputperson(String inputperson) {
        this.inputperson = inputperson;
    }

    public Msg(Map<String,Object> map) {
        this.id= (int) map.get("id");
        this.contain= (String) map.get("contain");
        this.inputtime= (String) map.get("inputtime");
        this.inputperson= (String) map.get("inputperson");
    }

}
