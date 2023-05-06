package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Charger;

import java.util.List;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-05 10:22
 */
public interface ChargerDao {
    /**
     * 获取配送员
     * @return
     */
    List<Charger> getCharger();

    /**
     * 根据配送员id查询配送员信息
     * @param chargerid
     * @return
     */
    Charger getChargerById(int chargerid);
}
