package com.syeinfo.catnip.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SecUtil {

    public static final String RSA_PUB_KEY = "RSA_PUB_KEY";
    public static final String RSA_PRV_KEY = "RSA_PRV_KEY";
    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    private static final int RSA_KEY_SIZE = 1024;

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static String generateAESKey() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String encryptAES(String raw, String hexKey) {

        try {

            SecretKeySpec keySpec = getAESSecretKeySpec(hexKey);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] byteContent = raw.getBytes(StandardCharsets.UTF_8);
            byte[] result = cipher.doFinal(byteContent);

            Hex hex = new Hex();

            return new String(hex.encode(result), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String decryptAES(String raw, String hexKey) {

        try {

            SecretKeySpec keySpec = getAESSecretKeySpec(hexKey);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            Hex hex = new Hex();

            byte[] byteContent = hex.decode(raw.getBytes(StandardCharsets.UTF_8));
            byte[] result = cipher.doFinal(byteContent);

            return new String(result, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String encryptMD5(String raw) {

        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte[] array = md5.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, String> generateRSAKey() {

        KeyPairGenerator keyGenerator;

        try {
            keyGenerator = KeyPairGenerator.getInstance("RSA");
            keyGenerator.initialize(RSA_KEY_SIZE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        KeyPair kp = keyGenerator.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        Hex hex = new Hex();
        String pubKey = new String(hex.encode(publicKey.getEncoded()), StandardCharsets.UTF_8);
        String prvKey = new String(hex.encode(privateKey.getEncoded()), StandardCharsets.UTF_8);

        Map<String, String> keyPair = new HashMap<>();
        keyPair.put(RSA_PUB_KEY, pubKey);
        keyPair.put(RSA_PRV_KEY, prvKey);

        return keyPair;

    }

    public static String encryptRSA(String raw, String rsaHexKey, boolean isPubKey) {

        Cipher cipher;

        try {

            setRsaKey(rsaHexKey, isPubKey);
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, isPubKey ? publicKey : privateKey);
            byte[] encryptByte = cipher.doFinal(raw.getBytes(StandardCharsets.UTF_8));

            Hex hex = new Hex();
            return new String(hex.encode(encryptByte), StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String decryptRSA(String raw, String rsaHexKey, boolean isPubKey) {

        Cipher cipher;

        try {

            setRsaKey(rsaHexKey, isPubKey);
            cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, isPubKey ? publicKey : privateKey);

            Hex hex = new Hex();
            byte[] decryptByte = cipher.doFinal(hex.decode(raw.getBytes(StandardCharsets.UTF_8)));

            return new String(decryptByte, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  null;

    }

    private static void setRsaKey(String keyHexStr, boolean isPubKey) throws Exception {

        if (isPubKey && null != publicKey || null != privateKey) {
            return;
        }

        Hex hex = new Hex();
        byte[] keyBytes = (byte[]) hex.decode(keyHexStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        if (isPubKey) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = keyFactory.generatePublic(keySpec);
        } else {
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(keyBytes);
            privateKey = keyFactory.generatePrivate(keySpec);
        }

    }

    private static SecretKeySpec getAESSecretKeySpec(String hexKey) throws DecoderException {

        Hex hex = new Hex();
        return new SecretKeySpec(hex.decode(hexKey.getBytes(StandardCharsets.UTF_8)), "AES");

    }

    public static void main(String[] args) {

//        Map<String, String> keyPair = generateRSAKey();
//        String pubKey = keyPair.get(SecUtil.RSA_PUB_KEY);
//        String prvKey = keyPair.get(SecUtil.RSA_PRV_KEY);

        String pubKey = "30819f300d06092a864886f70d010101050003818d003081890281810082ce0d189b503efd65304abf2f6a25e37083c0d00ee1d72e958ae01ea0e1884e9e7e1a3b61bb927e3ea58924eb94875bce9d6e8e94ee2a1a69f1ff74c62eb07c89f7a76dbc41788940afbecd4b8c308169f5a6bf81005f13ddba6a80c7e288afa8322c41dd7fdcb00a025eba20a3f7e8030e5c68494844e715cebb73d0eb444f0203010001";
        String prvKey = "30820276020100300d06092a864886f70d0101010500048202603082025c0201000281810082ce0d189b503efd65304abf2f6a25e37083c0d00ee1d72e958ae01ea0e1884e9e7e1a3b61bb927e3ea58924eb94875bce9d6e8e94ee2a1a69f1ff74c62eb07c89f7a76dbc41788940afbecd4b8c308169f5a6bf81005f13ddba6a80c7e288afa8322c41dd7fdcb00a025eba20a3f7e8030e5c68494844e715cebb73d0eb444f020301000102818005cbcb98c38912c177b2641d84a45779748ae20856207bc07cafe2ae78c2859fb63630436159466c81aa242196aec630b33f169f0aca62e338be986b3c3ce5650f2029503792375e45b2eed2ec2451a31089dce55ff4f03091fa8f2c6868bac3cadddcb921ae36dd82b4cb9961e96ea23df40c4d7d57e75d1805fa91120508c1024100b8dfed70e15f9c04a6d2ac5a017d5f16ccfb3dfa9cb87057c0c6d41041ef6c873061e1ac592a1a39f2638be4444bea172235ecda8d0d0928ffe1caafc6f3db65024100b520db19b10f17026428d6b1cb2ebfd70708bd76e35f313f1871b9164a41d1012f0755f4dd408df2132f8bc47304153fe6a35825372c022d911216eea76697a302410089e524e30487df04ad3608ae5ddc18b8160ef46ce0c6c5acfeb28f6cccf17bdede5cb40a6cbb75b27134851caf407c8be2cd9c2c6bf72434fb9f7c225e6077ed024072d9c6945fe5459dca8ceab60a343688ce79084a5e9a79cb9c606010763d8d9dc6884c44dc1696eb2ca73564f7d95af00dfafd657bba781714a9f7c89475452902406e12c690c9ac119b4619e4a8e076efa70566427654e3d37cd2b9e692fe5dc17a8537ad1db1e293df1d3b90520ee15a1e19e17863f7bf4ecbf6b9523e6b1c96a9";

//        String raw = "{\"account\":\"testacct1\",\"passwd\":\"7c4a8d09ca3762af61e59520943dc26494f8941b\"}";
        String raw = "{\"account\":\"testacct1\",\"token\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJHb2FsaWUiLCJqdGkiOiI3NDE3M2QwMC05ZjA3LTQ4YWYtYWE0ZS0xZTI1NmU1NzI5NmQiLCJhY2NvdW50IjoidGVzdGFjY3QxIn0.0ZptRSlEwa_pan_NPnPyvjXy_8SNyTtB_EZnIpboONg\"}";
        System.out.println("pub key: " + pubKey);
        System.out.println("prv key: " + prvKey);

//        String pubEncTxt = encryptRSA(raw, pubKey, true);
//        System.out.println(pubEncTxt);
//
//        String rawTxt = decryptRSA(pubEncTxt, prvKey, false);
//        System.out.println(rawTxt);

        String prvEncTxt = encryptRSA(raw, prvKey, false);
        System.out.println(prvEncTxt);

//        String prvEncTxt = "43036066379d64355698633b3a8bccad4b400491e16be9ca030d27dd33d13b3ce5370846614a700dda3e86cee61555f15f8a64c5f56094a9ab6adaae78570d10ed1909eb5d5d672d741d49bab3a136dc639a5449f3787ef702f6120dbb3e49db71d637a9a93f11969b624a11d4f06d804a3d8d43af95fdfa64acd0ac055ab09e";

//        String rawTxt2 = decryptRSA(prvEncTxt, pubKey, true);
//        System.out.println(rawTxt2);

//        String md5 = encryptMD5("7c4a8d09ca3762af61e59520943dc26494f8941b");
//        System.out.println(md5);

        String aesKey = generateAESKey();
        System.out.println(aesKey);
//        String aesEncTxt = encryptAES(raw, aesKey);
//        System.out.println(aesEncTxt);
//
//        String aesDecTxt = decryptAES(aesEncTxt, aesKey);
//        System.out.println(aesDecTxt);

    }

}
