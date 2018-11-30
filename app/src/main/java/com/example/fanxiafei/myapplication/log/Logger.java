package com.example.fanxiafei.myapplication.log;

import android.util.Log;

public class Logger {

    public static void d_2json(String TAG, String string) {
        Log.d(TAG, Json.format(string));
    }

}
