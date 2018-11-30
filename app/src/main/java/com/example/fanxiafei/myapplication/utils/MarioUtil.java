package com.example.fanxiafei.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

public class MarioUtil {
    @SuppressLint("HardwareIds")
    public static String getHashedAndroidId(Context context) {
        try {
             String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return HashUtils.getMd5(androidId);
        } catch (Exception var2) {
            return "";
        }
    }



    public static String getHashedIMEI(Context context) {
        String imei = getImei(context);
        return !TextUtils.isEmpty(imei) ? HashUtils.getMd5(imei) : null;
    }
    private static String sDeviceID;
    private static String getImei(Context context) {
        if (sDeviceID != null) {
            return sDeviceID;
        } else {
            try {
                List<String> imeiList = DeviceIdHelper.getImeiList(context);
                if (imeiList != null && imeiList.size() > 0) {
                    sDeviceID = (String)imeiList.get(imeiList.size() - 1);
                }

                return sDeviceID;
            } catch (Exception var2) {
                var2.printStackTrace();
                return null;
            }
        }
    }



    public static String randomString() {
        return HashUtils.getMd5(UUID.randomUUID().toString());
    }
}
