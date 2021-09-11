package com.atguigu;


import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Asc {
    public static void main(String[] args) throws Exception {
//        String a = "尚";
////        byte[] bytes = a.getBytes("GBK");
//        byte[] bytes = a.getBytes();
//        for (byte b : bytes) {
//            System.out.print(b + "   ");
//            String s = Integer.toBinaryString(b);
//            System.out.println(s);
//        }

        String input = "古城";
//        String key = "12345678";
        String key = "1234567887654321";

//        String algorithm = "des";
//        String transformation = "des";

        String algorithm = "aes";
        String transformation = "aes";

        String encode = encryptDES(input, key, transformation,algorithm );
        System.out.println(encode);

        String decode = decryptDES(encode, key, transformation, algorithm);
        System.out.println(decode);
    }

    private static String encryptDES(String input, String key, String transformation, String algorithm) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);

        SecretKeySpec sks = new SecretKeySpec(key.getBytes(),algorithm);

        cipher.init(Cipher.ENCRYPT_MODE,sks);

        byte[] bytes = cipher.doFinal(input.getBytes());

        String encode = Base64.encode(bytes);

        return encode;
    }

    private static String decryptDES(String input, String key, String transformation, String algorithm) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);

        SecretKeySpec sks = new SecretKeySpec(key.getBytes(),algorithm);

        cipher.init(Cipher.DECRYPT_MODE,sks);

        byte[] bytes = cipher.doFinal(Base64.decode(input));

        return new String(bytes);
    }
}
