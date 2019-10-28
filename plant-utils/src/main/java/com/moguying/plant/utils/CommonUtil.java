package com.moguying.plant.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public enum  CommonUtil {
    INSTANCE;

    private final String[] seedStr = {"0","Q","9","M","1","U","6","3","B","N","4","D","5","W","C","7","R","F","8","A","E","G","P",
            "Y","J","K","H","L","S","T","V","2","X","Z"};

    /**
     * 校验是否为手机号
     * @param phone
     * @return
     */
    public boolean isPhone(String phone){

        return Pattern.matches("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$",phone);
    }


    /**
     * 短信验证码
     * @return
     */
    public String messageCode(){
        return Integer.toString((int)(((Math.random()*9) + 1) * 100000));
    }


    /**
     * 身份证号校验
     * @param IDNumber
     * @return
     */
    public boolean isIDNumber(String IDNumber) {
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
        if (matches) {

            if (IDNumber.length() == 18) {
                try {
                    char[] charArray = IDNumber.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
                        return true;
                    } else {
                        return false;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return matches;
    }


    /**
     * 用户于微信生成签名序列字符
     * @param decript
     * @return
     */
    public String sha1Sign(String decript){
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            try {
                digest.update(decript.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 生成6位邀请码
     * @param  seed 生成因子
     * @return
     */
    public String createInviteCode(Integer seed){
        Integer mod = 0;
        LinkedList<String> stringLinkedList = new LinkedList<>();
        seed += 50000000;
        while (seed != 0){
            mod = seed % 34;
            seed = seed / 34;
            stringLinkedList.addFirst(seedStr[mod]);
        }
        StringBuilder sb = new StringBuilder();
        if(stringLinkedList.size() >= 6){
            for(String i : stringLinkedList){
                sb.append(i);
            }
            return sb.toString();
        } else {
            for(int i = 0 ; i < 6; i++){
                if(i < 6 - stringLinkedList.size()){
                    sb.append(seedStr[0]);
                } else {
                    sb.append(stringLinkedList.pollFirst());
                }
            }
            return sb.toString();
        }
    }

}
