package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.Address;
import com.iweb.tongda.project.dao.AddressDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 14:56
 */
public class AddressDaoImpl implements AddressDao {
    /**
     * 查询地址
     * @return
     */
    @Override
    public Address getAddress() {
        String sql = "select * from s_address";
        Address address = new Address();

        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        if (mapList.size() > 0) {
            address.setProvince((String) mapList.get(0).get("province"));
            address.setCity((String) mapList.get(0).get("city"));
        }
        return address;
    }

    /**
     * 修改地区信息
     * @param address
     * @return
     */
    @Override
    public boolean updateAddress(Address address) {
        String sql = "update s_address set province = ?,city = ?";
        int i = DbUtil.executeUpdate(sql,address.getProvince(),address.getCity());
        return i > 0 ? true: false;
    }
}
