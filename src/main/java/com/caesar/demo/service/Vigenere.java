package com.caesar.demo.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Vigenere {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static String standardLength(String text, String key){
        int textLength = text.length();
        int keyLength = key.length();
        String newKey = key;

        if(textLength>keyLength){
            int rep = (int)Math.ceil(((float)textLength)/keyLength);
            for(int i=0; i<rep;i++)
                newKey = newKey+key;
        }
        newKey = newKey.substring(0,textLength);

        return newKey;
    }

    public static String customEncrypt (String alphabet, String text, String key){
        List<String> alphabet_ = Arrays.asList(alphabet.split(""));
        int length = alphabet_.size();
        String key_ = standardLength(text, key);

        return generateCustomCipher(alphabet_,text, key_, length,1);
    };

    public static String checkAlphabet_ (String alphabet) {
        return "";
    };

    public static String customDecrypt (String alphabet, String text, String key){
        List<String> alphabet_ = Arrays.asList(alphabet.split(""));
        int length = alphabet_.size();
        String key_ = standardLength(text, key);

        return generateCustomCipher(alphabet_,text, key_, length,0);
    };

    private static String generateCustomCipher(List<String> alphabet, String text, String key, int length, int orientation){
        List<String> text_ = Arrays.asList(text.split(""));
        List<String> key_ = Arrays.asList(key.split(""));
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;

        for (String c : text_) {
            if (alphabet.contains(c)) {
                int pos = alphabet.indexOf(c);
                int posk = alphabet.indexOf(key_.get(keyIndex));

                //sentido a ->b o b <-a
                int posc = (orientation>0)? pos+posk: pos-posk;

                //ubicar la posicion
                int n_pos = (posc)%length;

                //para evitar que el index salga de la alfabeto
                if(n_pos<0) n_pos = length+ n_pos;
                if(n_pos>length) n_pos = n_pos-length;

                keyIndex++;
                result.append(alphabet.get(n_pos));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}

