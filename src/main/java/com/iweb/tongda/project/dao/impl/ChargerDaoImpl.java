package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Charger;
import com.iweb.tongda.project.dao.ChargerDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-05 10:23
 */
public class ChargerDaoImpl implements ChargerDao {
    /**
     * 获取配送员
     * @return
     */
    @Override
    public List<Charger> getCharger() {
        String sql = "select * from s_charger";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        List<Charger> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Charger charger = new Charger(map);
            list.add(charger);
        }
        return list;
    }

    /**
     * 根据配送员id查询配送员信息
     * @param chargerid
     * @return
     */
    @Override
    public Charger getChargerById(int chargerid) {
        String sql = "select * from s_charger where id = ?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql, chargerid);
        Charger charger = null;
        if (mapList.size() > 0) {
            charger = new Charger(mapList.get(0));
        }
        return charger;
    }
}
