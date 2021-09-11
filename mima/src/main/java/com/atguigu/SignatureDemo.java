package com.atguigu;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SignatureDemo {
    public static void main(String[] args) throws Exception {
        String a = "123";

        PublicKey publicKey = RSADemo.getPublicKey("a.pub","RSA" );
        PrivateKey privateKey = RSADemo.getPrivateKey("a.pri","RSA" );

        String signaturedData = getSignature(a, "sha256withrsa", privateKey);

        boolean b = verifySignature(a, "sha256withrsa", publicKey, signaturedData);

        System.out.println(b);
    }

    /**
     * 生成签名
     *
     * @param input      : 原文
     * @param algorithm  : 算法
     * @param privateKey : 私钥
     * @return : 签名
     * @throws Exception
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception {
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initSign(privateKey);
        // 传入原文
        signature.update(input.getBytes());
        // 开始签名
        byte[] sign = signature.sign();
        // 对签名数据进行Base64编码
        return Base64.encode(sign);
    }

    /**
     * 校验签名
     *
     * @param input          : 原文
     * @param algorithm      : 算法
     * @param publicKey      : 公钥
     * @param signaturedData : 签名
     * @return : 数据是否被篡改
     * @throws Exception
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signaturedData) throws Exception {
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initVerify(publicKey);
        // 传入原文
        signature.update(input.getBytes());
        // 校验数据
        return signature.verify(Base64.decode(signaturedData));

    }

}