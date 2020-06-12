package com.caesar.demo;

public class CaesarBody {
    String alphabet;
    String text;
    int key;

    public CaesarBody( String t, int k){
        this.text=t;
        this.key=k;
    }

    public CaesarBody(String a, String t, int k){
        this.alphabet = a;
        this.text=t;
        this.key=k;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CaesarBody{" +
                "alphabet='" + alphabet + '\'' +
                ", text='" + text + '\'' +
                ", key=" + key +
                '}';
    }
}
