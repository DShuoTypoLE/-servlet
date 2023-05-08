package com.iweb.tongda.project.dao.impl;

import com.iweb.tongda.project.bean.UpLoadImg;
import com.iweb.tongda.project.dao.UpLoadImgDao;
import com.iweb.tongda.project.util.DbUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-08 15:42
 */
public class UpLoadImgDaoImpl implements UpLoadImgDao {
    /**
     * 添加上传图片
     * @param uploadImg
     * @return
     */
    @Override
    public boolean addImg(UpLoadImg uploadImg) {
        String sql = "insert into s_uploadimg(imgName,imgSrc,imgType) values(?,?,?)";
        int i = DbUtil.executeUpdate(sql,
                uploadImg.getImgName(),
                uploadImg.getImgSrc(),
                uploadImg.getImgType());
        return i > 0 ? true: false;
    }

    /**
     * 根据图片名称得到图片id
     * @param uploadImg
     * @return
     */
    @Override
    public int getImgId(UpLoadImg uploadImg) {
        String sql = "select imgId from s_uploadimg where imgName = '"+ uploadImg.getImgName() + "'";
        List<Map<String, Object>> mapList = DbUtil.executeQuery(sql);
        int imgId = 0;
        if (mapList.size() > 0) {
            imgId = (int) mapList.get(0).get("imgId");
        }
        return imgId;
    }

}
