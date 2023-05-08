package com.iweb.tongda.project.dao;

import com.iweb.tongda.project.bean.UpLoadImg;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-08 15:41
 */
public interface UpLoadImgDao {
    /**
     * 添加上传图片
     * @param uploadImg
     * @return
     */
    boolean addImg(UpLoadImg uploadImg);

    /**
     * 根据图片名称得到图片id
     * @param uploadImg
     * @return
     */
    int getImgId(UpLoadImg uploadImg);

}
