package com.caesar.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CaesarCipher {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String encrypt(String plainText, int key) {
        return customEncrypt(alphabet,plainText, key);
    }

    public static String decrypt(String cipherText, int key) {
        return customEncrypt(alphabet,cipherText, -key);
    }

    public static String checkAlphabet(String alphabet_){
        return StringUtils.isNotBlank(alphabet_)?alphabet_: alphabet;
    }

    public static String customEncrypt (String alphabet, String text, int key){
        List<String> alphabet_ = Arrays.asList(checkAlphabet(alphabet).split(""));
        int length = alphabet_.size();

        return generateCustomCipher(alphabet_,text, key, length);
    };

    public static String customDecrypt (String alphabet, String text, int key){
        return  customEncrypt(alphabet, text,-key);
    };

    private static String generateCustomCipher(List<String> alphabet, String text, int key, int length){
        List<String> text_ = Arrays.asList(text.split(""));
        StringBuilder result = new StringBuilder();

        for (String c : text_) {
            if (alphabet.contains(c)) {
                int pos = alphabet.indexOf(c);
                int n_pos = (pos+key)%length;

                //para evitar que el index salga de la lista
                if(n_pos<0) n_pos = length+ n_pos;

                result.append(alphabet.get(n_pos));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

}

