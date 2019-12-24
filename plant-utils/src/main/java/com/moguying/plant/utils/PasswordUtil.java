package com.moguying.plant.utils;

import java.security.NoSuchAlgorithmException;

/**
 * <p>Title: Password</p>
 * <p>Description: </p>
 *
 * @author Qinhir
 * @date 2018-10-23 14:38
 */
public enum PasswordUtil {
    INSTANCE;


    public String encode(byte[] source) {
        return encode(source, 16);
    }


    public String encode(byte[] source, Integer length) {
        String s = null;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte[] tmp = md.digest();
            if (null == length) length = tmp.length;
            char[] str = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }


}
