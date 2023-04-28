package com.iweb.tongda.project.test;

import com.iweb.tongda.project.util.SaltMD5Util;
import org.junit.jupiter.api.Test;


/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-04-28 21:23
 */
public class TestMD5 {

    @Test
    public void test01(){
        String password = "123456";
        String pwdFromDB = SaltMD5Util.generateSaltPassword(password);
        System.out.println("pwdFromDB=" + pwdFromDB);
        System.out.println(SaltMD5Util.verifySaltPassword(password,pwdFromDB));
    }
    @Test
    public void test02(){
        String password = "";
        String pwdFromDB = SaltMD5Util.generateSaltPassword(password);
        System.out.println("pwdFromDB=" + pwdFromDB);
        System.out.println(SaltMD5Util.verifySaltPassword(password,pwdFromDB));
    }
    @Test
    public void test03(){
        String password = null;
        String pwdFromDB = SaltMD5Util.generateSaltPassword(password);
        System.out.println("pwdFromDB=" + pwdFromDB);
        System.out.println(SaltMD5Util.verifySaltPassword(password,pwdFromDB));
    }

}
