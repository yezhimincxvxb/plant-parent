package com.moguying.plant.utils;

import cfca.sadk.algorithm.common.Mechanism;
import cfca.sadk.algorithm.common.PKIException;
import cfca.sadk.lib.crypto.JCrypto;
import cfca.sadk.system.FileHelper;
import cfca.sadk.util.*;
import cfca.sadk.x509.certificate.X509Cert;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;


/**
 * 由乾易付提供
 * 2019-06-28
 */
public class CFCARAUtil {
    private static final String deviceName = JCrypto.JSOFT_LIB;
    private static cfca.sadk.lib.crypto.Session session = null;


    static {
        try {
            JCrypto jCrypto = JCrypto.getInstance();
            jCrypto.initialize(deviceName, null);
            session = jCrypto.openSession(deviceName);
        } catch (PKIException e) {
            e.printStackTrace();
        }
    }


    /******* p1 *********/
    /**
     *
     * p1消息签名
     * @param ：message
     * @return
     * @throws Exception
     *
     */
    public static String signMessageByP1(String message, String pfxPath, String passWord) throws Exception{
        InputStream inputStream = new ClassPathResource(pfxPath).getInputStream();
        PrivateKey userPriKey = KeyUtil.getPrivateKeyFromPFX(inputStream, passWord);
        Signature signature = new Signature();
        byte[] base64P7SignedData = signature.p1SignMessage(Mechanism.SHA256_RSA, message.getBytes("UTF-8"), userPriKey, session);
        return new String(base64P7SignedData);
    }

    /**
     * p1消息校验(公钥证书验签)
     * @param ：beforeSignedData
     * @param ：afterSignedData
     * @param :certPath
     * @return
     * @throws Exception
     */
    public static boolean verifyMessageByP1(String beforeSignedData, String afterSignedData, String certPath) throws Exception{
        InputStream file = new ClassPathResource(certPath).getInputStream();
        X509Cert cert = new X509Cert(file);
        PublicKey publicKey = cert.getPublicKey();
        Signature signature = new Signature();
        return signature.p1VerifyMessage(Mechanism.SHA256_RSA, beforeSignedData.getBytes("UTF-8"), afterSignedData.getBytes("UTF-8"), publicKey, session);
    }
    /**
     * p1消息校验(公钥证书验签)
     * @return
     * @throws Exception
     */
    public static boolean verifyMessageByP1Location(String beforeSignedData, String afterSignedData, String certPath) throws Exception{
        X509Cert cert = new X509Cert(new FileInputStream(certPath));
        PublicKey publicKey = cert.getPublicKey();
        Signature signature = new Signature();
        return signature.p1VerifyMessage(Mechanism.SHA256_RSA, beforeSignedData.getBytes("UTF-8"), afterSignedData.getBytes("UTF-8"), publicKey, session);
    }

    /**
     *
     * p1消息校验(公钥字符串验签)
     * @param ：beforeSignedData
     * @param ：afterSignedData
     * @param :publicKeyStr
     * @return
     * @throws Exception
     *
     */
    public static boolean verifyMessageByP1AndPubKey(String beforeSignedData, String afterSignedData, String publicKeyStr) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr));
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        Signature signature = new Signature();
        return signature.p1VerifyMessage(Mechanism.SHA256_RSA, beforeSignedData.getBytes("UTF-8"), afterSignedData.getBytes("UTF-8"), publicKey, session);
    }

    /********* RSA_PKCS ***********/
    /**
     *  RSA证书加密消息
     *  RSA_PKCS公钥加密
     * @param ：message
     * @throws Exception
     */
    public static String encryptMessageByRSA_PKCS(String message, String certPath) throws Exception{
        InputStream file = new ClassPathResource(certPath).getInputStream();
        X509Cert cert = new X509Cert(file);
        PublicKey userPubKey = cert.getPublicKey();
        Mechanism mechanism = new Mechanism(Mechanism.RSA_PKCS);
        byte[] encryptedData = EncryptUtil.encrypt(mechanism, userPubKey, message.getBytes("UTF-8"), session);
        return new String(encryptedData);
    }

    /**
     *  RSA证书加密消息
     *  RSA_PKCS公钥字符串加密
     * @param ：message
     * @throws Exception
     */
    public static String encryptMessageByRSA_PKCS_String(String message, String publicKeyStr) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr));
        PublicKey userPubKey = keyFactory.generatePublic(keySpec);

//        File file = new ClassPathResource(certPath).getFile();
//        X509Cert cert = new X509Cert(new FileInputStream(file));
//        PublicKey userPubKey = cert.getPublicKey();
        Mechanism mechanism = new Mechanism(Mechanism.RSA_PKCS);
        byte[] encryptedData = EncryptUtil.encrypt(mechanism, userPubKey, message.getBytes("UTF-8"), session);
        return new String(encryptedData);
    }

    /**
     * RSA证书解密消息
     * RSA_PKCS私钥解密
     * @param :key
     * @param ：message
     * @throws Exception
     */
    public static String decryptMessageByRSA_PKCS(String message, String pfxPath, String passWord) throws Exception{
        InputStream file = new ClassPathResource(pfxPath).getInputStream();
        PrivateKey userPriKey = KeyUtil.getPrivateKeyFromPFX(file, passWord);
        Mechanism mechanism = new Mechanism(Mechanism.RSA_PKCS);
        byte[] dataBytes = message.getBytes("UTF-8");
        byte[] encryptedData = EncryptUtil.decrypt(mechanism, userPriKey, dataBytes, session);
        return new String(encryptedData);
    }

    /********* RC4 **********/
    /**
     * RSA证书加密消息
     * RC4对称加密
     * @param ：message
     * @return
     * @throws Exception
     */
    public static String encryptMessageByRC4(String message, String pfxPath, String passWord) throws Exception{
        byte[] data = FileHelper.read(pfxPath);
        Key key = new SecretKeySpec(Base64.decode(Base64.encode(data)), "RC4");
        Mechanism mechanism = new Mechanism(Mechanism.RC4);
        byte[] dataBytes = message.getBytes("UTF-8");
        byte[] encryptedData = EncryptUtil.encrypt(mechanism, key, dataBytes, session);
        return new String(encryptedData);

    }

    /**
     * RSA证书解密消息
     * RC4对称解密
     * @param ：message
     * @return
     * @throws Exception
     */
    public static String decryptMessageByRC4(String message, String pfxPath, String passWord) throws Exception{
        byte[] data = FileHelper.read(pfxPath);
        Key key = new SecretKeySpec(Base64.decode(Base64.encode(data)), "RC4");
        Mechanism mechanism = new Mechanism(Mechanism.RC4);
        byte[] encryptedData = EncryptUtil.decrypt(mechanism, key, message.getBytes("UTF-8"), session);
        return new String(encryptedData);
    }

    /****** p7 ******/
    /**
     * P7 分离式文件签名（签名）
     */
    public static String signData(String toBeSigned, String certPath, String certPass) throws Exception {
        X509Cert cert = CertUtil.getCertFromPFX(certPath, certPass);
        PrivateKey priKey = KeyUtil.getPrivateKeyFromPFX(certPath, certPass);
        Signature signature = new Signature();
        return new String(signature.p7SignMessageDetach(Mechanism.SHA256_RSA, toBeSigned.getBytes("UTF8"), priKey, cert, session), "UTF8");
    }

    /**
     * P7 分离式消息校验（验签）
     */
    public static boolean verifySignature(String data, String signdata) throws Exception {
        Signature signature = new Signature();
        return signature.p7VerifyMessageDetach(data.getBytes("UTF8"), signdata.getBytes("UTF8"), session);
    }

    /**
     * 消息数字信封（公钥加密）
     */
    public static String encryptData(String data, String certPath) throws Exception {
        // X509Cert cert = CertUtil.getCertFromPFX(certPath, certPass);
        X509Cert cert = new X509Cert(new FileInputStream(certPath));
        X509Cert[] recvcerts = new X509Cert[]{ cert };
        return new String(EnvelopeUtil.envelopeMessage(data.getBytes("UTF8"), Mechanism.RC4, recvcerts, session), "UTF8");
    }

    /**
     * 数据解密
     */
    public static String decryptData(String encryptedData, String certPath, String certPass) throws Exception {
        PrivateKey priKey = KeyUtil.getPrivateKeyFromPFX(certPath, certPass);
        X509Cert cert = CertUtil.getCertFromPFX(certPath, certPass);
        return new String(EnvelopeUtil.openEvelopedMessage(encryptedData.getBytes("UTF8"), priKey, cert, session), "UTF8");
    }



    /**
     * 拼接字符串方法
     * @param map
     * @param connector
     * @return
     */
    public static String joinMapValue(Map<String, Object> map, char connector)	{
        StringBuffer b = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            b.append(entry.getKey());
            b.append('=');
            if (entry.getValue() != null){
                b.append(entry.getValue());
            }
            b.append(connector);
        }
        return b.toString().substring(0, b.length()-1);
    }


    public static void main(String[] args) throws Exception {
        // 实际地址，请改为项目路径
        String pfxPath = "D:\\certificate\\168885_test.pfx";
        // 实际地址，请改为项目路径
        String certPath = "D:\\certificate\\168885_test.cer";
        String password = "123123";
        String plaintext = "幸福是你有食物吃,睡觉的地方,有所爱的人。";
        String plaintext1 = "幸福是你有食物吃,睡觉的地方,有所爱的人";

        /******* p1 ******/
        //签名
        String base64P7SignedData = signMessageByP1(plaintext, pfxPath, password);
        //验签
        boolean verifyByp1 = verifyMessageByP1(plaintext, base64P7SignedData, certPath);
        System.out.println("p1-cert:"+verifyByp1);

        //验签
        String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApSC4H4PvPuS9GJq9chCHq"
                + "PHb+MK2dYRwVlU+9LJHhEA0mbmkhbSyvcakHuvrXtrBCBt5GMSU2BQeZy2IqQoZDJ"
                + "Cn5CHufgMUpyMD7qvRo+GOg3GRC3k506ebb/Od/LL0eMAcCiOcCC7HHiPGP44VtBs"
                + "OgqX22/BSAxyK93bnQbb4+8sc4id0io403rLjBle7vIzrNJtqftuTSQJMm/OmRDvf"
                + "hg0asdUZYCsb3TdhRqO5hblDl/s/5b6gFTYcgPAw9qKdknqAWGqHP/J6i3GDAqedq"
                + "7lFuDvkqSnYnWgVzpv9luWzrvXYOl2K4fvDSl9JIXHUMMz9cELEJjmq7yM+fQIDAQ"
                + "AB";
        boolean verifyByp1PublicKeyStr = verifyMessageByP1AndPubKey(plaintext, base64P7SignedData, publicKeyStr);
        System.out.println("p1-PublicKeyStr:"+verifyByp1PublicKeyStr);

        File file = new File("D:\\certificate\\168885_test.cer");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate)cf.generateCertificate(new FileInputStream(file));
        PublicKey publicKey = cert.getPublicKey();
        String publicKeyString = Base64.toBase64String(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");
    }





}
