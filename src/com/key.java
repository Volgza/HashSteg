package com;

import java.security.SecureRandom;

import static com.variable.arrayKey;

public class key {
    static void getKey() {
        SecureRandom randomKey = new SecureRandom();
        int length = 3;
        short[] arrayKey = new short[length];
        for (int l = 0; l < length; l++) {
            arrayKey[l] = (short) randomKey.nextInt();
        }
    }
}
