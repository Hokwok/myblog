package com.kwok.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 密码等信息加密
 */
public class MD5Utils {

    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    public static String code(String str){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest=md.digest();
            int i;
            StringBuffer buffer=new StringBuffer("");
            for(int offset=0;offset<byteDigest.length;offset++){
                i=byteDigest[offset];
                if (i<0)
                    i+=256;
                if (i<16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            //32位加密
            return buffer.toString();
            //16位的加密
            //return buffer.toString.substring(8,24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据email获取gravatar头像
    public static String getGravatar(String email) {
        String emailMd5 = code(email);
        //设置图片大小32px
        String avatar = "http://www.gravatar.com/avatar/"+emailMd5+"?s=32";
        return avatar;
    }

    public static void main(String[] args) {
        System.out.println(code("123"));
    }
}
