package com.iweb.tongda.project.test;

import org.junit.Test;

import java.util.UUID;

/**
 * @author 丁硕
 * @version 1.0
 * @Date 2023-05-11 19:16
 */
public class TestUUID {
    @Test
    public void test01(){
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer buffer = null;
        for (int i = 0; i < 13; i++) {
             buffer = stringBuffer.append((int) (Math.random()*10));
        }
        System.out.println(buffer);
    }
}
