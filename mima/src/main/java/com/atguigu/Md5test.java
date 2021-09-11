package com.atguigu;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.MessageDigest;

public class Md5test {
    public static void main(String[] args) throws Exception{
        String input = "aa";
        String algorithm = "MD5";
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] bytes = digest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte aByte : bytes) {
            String s = Integer.toHexString(aByte & 0xff);
            if (s.length() == 1){
                s = "0" + s;
            }
            sb.append(s);
        }
        System.out.println(sb);
    }
}