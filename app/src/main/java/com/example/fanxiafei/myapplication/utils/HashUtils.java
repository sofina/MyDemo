package com.example.fanxiafei.myapplication.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtils {
    private static final String TAG = "HashUtils";
    private static final String[] mHexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private HashUtils() {
    }

    public static String getMd5(byte[] data) {
        String hashString = "";

        try {
            MessageDigest lDigest = MessageDigest.getInstance("MD5");
            lDigest.update(data);
            BigInteger lHashInt = new BigInteger(1, lDigest.digest());
            hashString = String.format("%1$032X", lHashInt);
        } catch (Exception var4) {
            ;
        }

        return hashString.toLowerCase();
    }

    public static String getMd5(String value) {
        if (value == null) {
            value = "";
        }

        return getMd5(value.getBytes());
    }




}
