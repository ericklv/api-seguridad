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
        reset();
    }

    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(sbox, 0);
    }

    public String encryptMessage(String message, String key) {
        reset();
        setKey(key);
        if(Objects.isNull(this.key)) return key_error;
        byte[] crypt = crypt(message.getBytes());
        reset();

        return toHexString(crypt);
    }

    public String decryptMessage(String message, String key) {
        reset();
        setKey(key);
        if(Objects.isNull(this.key)) return key_error;
        byte[] msg = crypt(toByteArray(message));
        reset();

        return new String(msg);
    }

    public byte[] crypt(final byte[] msg) {
        sbox = initSBox(key);
        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; n++) {
            i = (i + 1) % SBOX_LENGTH;
            j = (j + sbox[i]) % SBOX_LENGTH;
            swap(i, j, sbox);
            int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }

    private int[] initSBox(byte[] key) {
        int[] sbox = new int[SBOX_LENGTH];
        int j = 0;

        for (int i = 0; i < SBOX_LENGTH; i++) sbox[i] = i;

        for (int i = 0; i < SBOX_LENGTH; i++) {
            j = (j + sbox[i] + (key[i % key.length]) & 0xFF) % SBOX_LENGTH;
            swap(i, j, sbox);
        }
        return sbox;
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public void setKey(String key) {
        if (key.length() >= KEY_MIN_LENGTH && key.length() < SBOX_LENGTH) {
            this.key = key.getBytes();
        }else {
            this.key = null;
        }
    }

    public String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}

