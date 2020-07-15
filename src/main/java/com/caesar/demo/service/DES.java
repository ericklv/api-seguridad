package com.caesar.demo.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {

    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    //inicializa DES
    public DES(SecretKey key) throws Exception {
        encryptCipher = Cipher.getInstance("DES");
        decryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encryptBase64 (String unencryptedString) throws Exception {
        // Convierte el string en array de bytes
        byte[] unencryptedByteArray = unencryptedString.getBytes("UTF8");
        // encriptar
        byte[] encryptedBytes = encryptCipher.doFinal(unencryptedByteArray);
        // convierte a un array de bytes de base64
        byte [] encodedBytes = Base64.encodeBase64(encryptedBytes);

        return new String(encodedBytes);
    }

    public String decryptBase64 (String encryptedString) throws Exception {
        //obtenemos el array de bytes en base64 a partir de encryptedString
        byte [] decodedBytes = Base64.decodeBase64(encryptedString.getBytes());
        // desencriptar
        byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);
        // convertimos el array de bytes a string
        return new String(unencryptedByteArray, "UTF8");
    }

    public static String encrypt( String text, String key_) {
        DESKeySpec key = null;
        String encryptedString = null;
        try {
            key = new DESKeySpec(fixKey(key_).getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //Inicializamos para encriptar
            DES crypt = new DES(keyFactory.generateSecret(key));
            encryptedString = crypt.encryptBase64(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  encryptedString;
    }

    public static String decrypt( String text, String key_) {
        DESKeySpec key = null;
        String decryptedString = null;
        try {
            key = new DESKeySpec(fixKey(key_).getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //Inicializamos para descencriptar
            DES crypt = new DES(keyFactory.generateSecret(key));
            decryptedString = crypt.decryptBase64(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  decryptedString;
    }

    public static String fixKey (String key) {
        //Completa la key en caso no tenga una longitud minima de 8 caracteres
       return StringUtils.leftPad(key, 8, "0");
    };
}