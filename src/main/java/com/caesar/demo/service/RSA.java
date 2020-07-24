package com.caesar.demo.service;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;

public class RSA {

    final static int length = 1024;
    static String kPublic = "";
    static String kPrivate = "";

    public static String getkPrivate() {
        return kPrivate;
    }

    public static String getkPublic() {
        return kPublic;
    }

    public static HashMap<String, String> generateKeys() {
        HashMap<String, String> keys = new HashMap<String, String>();
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(length);
            KeyPair kp = kpg.genKeyPair();

            keys.put("public", bytesToString(kp.getPublic().getEncoded()));
            keys.put("private", bytesToString(kp.getPrivate().getEncoded()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keys;
    };

    public static String encrypt(String plain) {

        String encrypted = "";
        byte[] encryptedBytes;

        HashMap<String, String> keys = generateKeys();
        PublicKey publicKey = getPublicKeyString(keys.get("public"));

        kPublic = keys.get("public");
        kPrivate = keys.get("private");

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedBytes = cipher.doFinal(plain.getBytes());

            encrypted = bytesToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;

    };

    public static String decrypt(String result, String kPrivate) {
        String decrypted = "";

        byte[] decryptedBytes;
        PrivateKey privateKey = getPrivateKeyString(kPrivate);

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedBytes = cipher.doFinal(stringToBytes(result));
            decrypted = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    };

    public static byte[] stringToBytes(String s) {
        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    };

    public static PublicKey getPublicKeyString(String kPublic) {
        PublicKey pubKey = null;
        try {
            byte[] publicBytes = stringToBytes(kPublic);
            System.out.println("publicBytes "+publicBytes.length);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            pubKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pubKey;
    };

    public static PrivateKey getPrivateKeyString(String kPrivate) {
        PrivateKey privKey = null;
        try {
            byte[] privateBytes = stringToBytes(kPrivate);
            PKCS8EncodedKeySpec keySpecP = new PKCS8EncodedKeySpec(privateBytes);
            KeyFactory keyFactory2 = KeyFactory.getInstance("RSA");
            privKey = keyFactory2.generatePrivate(keySpecP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return privKey;
    };
}