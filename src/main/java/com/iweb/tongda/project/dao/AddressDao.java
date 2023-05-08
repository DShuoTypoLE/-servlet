package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.Address;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-26 14:56
 */
public interface AddressDao {
    /**
     * 查询地址
     * @return
     */
    Address getAddress();

    /**
     * 修改地区信息
     * @param address
     * @return
     */
    boolean updateAddress(Address address);
}
