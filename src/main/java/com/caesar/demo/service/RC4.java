package com.caesar.demo.service;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Objects;

public class RC4 {
    private static final int SBOX_LENGTH = 256;
    private static final int KEY_MIN_LENGTH = 3;
    private static String key_error = "la longitud del key debe estar " + KEY_MIN_LENGTH + " y " + (SBOX_LENGTH - 1);

    private byte[] key = new byte[SBOX_LENGTH - 1];
    private int[] sbox = new int[SBOX_LENGTH];

    public RC4() {
        //nos aseguramos de limpiar key y sbox al iniciar
        reset();
    }

    //funcion que limpia los valores del key y el sbox
    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(sbox, 0);
    }

    //funcion que encripta rc4 y devuelve un hex
    public String encryptMessage(String message, String key) {
        reset();
        setKey(key);
        //verifica si la key esta en el rango
        if(Objects.isNull(this.key)) return key_error;
        //genera el array de bytes
        byte[] crypt = crypt(message.getBytes());
        reset();

        return toHexString(crypt);
    }
    //funcion que desencripta rc4 y devuelve el texto original
    public String decryptMessage(String message, String key) {
        reset();
        setKey(key);
        //verifica si la key esta en el rango
        if(Objects.isNull(this.key)) return key_error;
        //genera el array de bytes
        byte[] msg = crypt(toByteArray(message));
        reset();

        return new String(msg);
    }

    public byte[] crypt(final byte[] msg) {
        //basado en el algoritmo rc4 en wikipedia
        sbox = initSBox(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        //aplicamos el PRGA
        for (int n = 0; n < msg.length; n++) {
            //usamos 2 contadores i y j,
            // i = (i+1) mod 256
            i = (i + 1) % SBOX_LENGTH;
            // j = sbox(i) mod 256
            j = (j + sbox[i]) % SBOX_LENGTH;
            //intercambio
            swap(i, j, sbox);
            //t = (S[i] + S[j]) mod 256;
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }
    //Se encarga de llenar el sbox con valores entre 0 a 255
    private int[] initSBox(byte[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        //llenamos el sbox
        for (int i = 0; i < SBOX_LENGTH; i++) sbox[i] = i;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + (key[i % key.length]) & 0xFF) % SBOX_LENGTH;
            //intercambia valores dentro del sbox
            swap(i, j, sbox);
        }
        return sbox;
    }

    //funcion que se encarga de intercambiar valores en el sbox
    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    //seteamos el valor del key, en caso no estar en el rango definido lo seteamos como null
    public void setKey(String key) {
        if (key.length() >= KEY_MIN_LENGTH && key.length() < SBOX_LENGTH) {
            this.key = key.getBytes();
        }else {
            this.key = null;
        }
    }

    //Toma una array de bytes y lo convierte a un hex
    public String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    //Toma un hex y lo convierte a array de bytes
    public byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}

