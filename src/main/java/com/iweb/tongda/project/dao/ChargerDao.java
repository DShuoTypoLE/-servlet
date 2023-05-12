package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Charger;
import com.iweb.tongda.project.bean.PageBean;

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

    /**
     * 查询配送员数量
     * @return
     */
    long allChargerCount();

    /**
     * 根据分页对象查询每页的数据
     * @param pageBean
     * @return
     */
    List<Charger> getChargerByLike(PageBean pageBean);

    /**
     * 添加配送员
     * @param charger
     * @return
     */
    boolean addCharger(Charger charger);

    /**
     * 更新配送员信息
     * @param charger
     * @return
     */
    boolean updateCharger(Charger charger);

    /**
     * 删除配送员信息
     * @param chargerId
     * @return
     */
    boolean delCharger(int chargerId);
}
