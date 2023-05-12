package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Charger;
import com.iweb.tongda.project.bean.PageBean;
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

    /**
     * 查询配送员数量
     * @return
     */
    @Override
    public long allChargerCount() {
        String sql = "select count(1) as count from s_charger";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);

        return mapList.size() > 0 ? (long) mapList.get(0).get("count") : 0;
    }

    /**
     * 根据分页对象查询每页的数据
     * @param pageBean
     * @return
     */
    @Override
    public List<Charger> getChargerByLike(PageBean pageBean) {
        String sql = "select * from s_charger limit ?,?";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql,
                (pageBean.getCurPage() - 1) * pageBean.getMaxSize(),
                pageBean.getMaxSize());
        List<Charger> list = new ArrayList<>();
        if (mapList.size() > 0){
            for (Map<String, Object> map : mapList) {
                Charger charger = new Charger(map);
                list.add(charger);
            }
        }
        return list;
    }

    /**
     * 添加配送员
     * @param charger
     * @return
     */
    @Override
    public boolean addCharger(Charger charger) {
        String sql = "insert into s_charger(name,phone,no) values(?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                charger.getName(),
                charger.getPhone(),
                charger.getNo());
        return i > 0 ? true: false;
    }

    /**
     * 更新配送员信息
     * @param charger
     * @return
     */
    @Override
    public boolean updateCharger(Charger charger) {
        String sql = "update s_charger set name = ?," +
                "phone = ? where id = ?";
        int i = DbUtil.executeUpdate(sql,
                charger.getName(),
                charger.getPhone(),
                charger.getId());
        return i > 0 ? true: false;
    }

    /**
     * 删除配送员信息
     * @param chargerId
     * @return
     */
    @Override
    public boolean delCharger(int chargerId) {
        String sql = "delete from s_charger where id = ?";
        int i = DbUtil.executeUpdate(sql, chargerId);
        return i > 0 ? true: false;
    }
}
