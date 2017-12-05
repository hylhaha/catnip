package com.syeinfo.catnip;

import com.syeinfo.catnip.utils.SecUtil;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JWTTest {

    public static void main(String[] args) throws Exception {

        Hex hex = new Hex();
        String str = "hello 测试";

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);

        KeyPair kp = keyGenerator.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        String pubKey = new String(hex.encode(publicKey.getEncoded()), StandardCharsets.UTF_8);
        System.out.println(pubKey);

        String prvKey = new String(hex.encode(privateKey.getEncoded()), StandardCharsets.UTF_8);
        System.out.println(prvKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] encryptByte = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));

        String encryptTxt = new String(hex.encode(encryptByte), StandardCharsets.UTF_8);
        System.out.println(encryptTxt);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] decryptByte = cipher.doFinal(hex.decode(encryptTxt.getBytes(StandardCharsets.UTF_8)));
        String decryptTxt = new String(decryptByte, StandardCharsets.UTF_8);
        System.out.println(decryptTxt);

        String md5 = SecUtil.encryptMD5("12123132");
        System.out.println(md5);

    }

}
