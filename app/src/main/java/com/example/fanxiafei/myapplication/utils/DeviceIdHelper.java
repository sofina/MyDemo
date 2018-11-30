package com.example.fanxiafei.myapplication.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeviceIdHelper {

    static final int IMEI_LENGTH = 15;
    static final int MEID_LENGTH = 14;

    public static String TYPE_IMEI1 = "1";
    public static String TYPE_IMEI2 = "2";
    public static String TYPE_MEID = "3";
    public static String TYPE_UNKOWN = "-1";

    private static Method sGetProp;
    private static Method sGetImeiList;
    private static Method sGetMeidList;
    private static Object sObjTelephonyManagerEx;

    private static String sIMEI1 = null;
    private static String sIMEI2 = null;
    private static Method sGetImeiForSlot;
    static {
        try {
            Class clsSystemProperties =
                    Class.forName("android.os.SystemProperties");
            sGetProp = clsSystemProperties.getMethod("get", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class clsTelephonyManagerEx =
                    Class.forName("miui.telephony.TelephonyManagerEx");
            sObjTelephonyManagerEx =
                    clsTelephonyManagerEx.getMethod("getDefault").invoke(null);
            sGetImeiList = clsTelephonyManagerEx.getMethod("getImeiList");
            sGetMeidList = clsTelephonyManagerEx.getMethod("getMeidList");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {// try android interface
            if (Build.VERSION.SDK_INT >= 21) {
                Class clsTelephonyManager = Class.forName("android.telephony.TelephonyManager");
                sGetImeiForSlot = clsTelephonyManager.getMethod("getImei", int.class);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /*
     * return String[0]：imei
     * String[1]：TYPE_IMEI1,TYPE_IMEI2,TYPE_MEID,TYPE_UNKOWN <p>Requires
     * Permission: {@link android.Manifest.permission#READ_PHONE_STATE
     * READ_PHONE_STATE}
     */
    @SuppressLint("MissingPermission")
    public static String[] getDeviceId(Context context) {
        String imei1 = getProp("ro.ril.miui.imei0");
        if (!isMultiSim()) {
            if (TextUtils.isEmpty(imei1)) {
                imei1 = getProp("ro.ril.miui.imei");
                if (imei1 != null && imei1.contains(",")) {
                    imei1 = imei1.split(",")[0];
                }
            }
            if (TextUtils.isEmpty(imei1)) {
                imei1 = getProp("ro.ril.oem.imei");
            }
            if (TextUtils.isEmpty(imei1)) {
                imei1 = getProp("persist.radio.imei");
            }
            if (isIMEIFormat(imei1)) {
                sIMEI1 = imei1;
                return new String[] {
                        imei1, TYPE_IMEI1
                };
            }
        } else {
            String imei2 = getProp("ro.ril.miui.imei1");
            String imeis = getProp("ro.ril.miui.imei");
            if (!TextUtils.isEmpty(imeis)) {
                String[] imeiArray = imeis.split(",");
                if (imeiArray.length == 2) {
                    if (TextUtils.isEmpty(imei1)) {
                        imei1 = imeiArray[0];
                    }
                    if (TextUtils.isEmpty(imei2)) {
                        imei2 = imeiArray[1];
                    }
                }
            }
            if (TextUtils.isEmpty(imei1)) {
                imei1 = getProp("ro.ril.oem.imei1");
            }
            if (TextUtils.isEmpty(imei1)) {
                imei1 = getProp("persist.radio.imei1");
            }
            if (TextUtils.isEmpty(imei2)) {
                imei2 = getProp("ro.ril.oem.imei2");
            }
            if (TextUtils.isEmpty(imei2)) {
                imei2 = getProp("persist.radio.imei2");
            }
            if (Build.VERSION.SDK_INT < 21) { // kk 国内版本只有一个IMEI
                String modDevice = getProp("ro.product.mod_device");
                if (modDevice == null || !modDevice.contains("_global")) {
                    if (isIMEIFormat(imei1)) {
                        sIMEI1 = imei1;
                        return new String[] {
                                imei1, TYPE_IMEI1
                        };
                    }
                    if (isIMEIFormat(imei2)) {
                        sIMEI1 = imei2;
                        return new String[] {
                                imei2, TYPE_IMEI1
                        };
                    }
                }
                if (isIMEIFormat(imeis)) {
                    sIMEI1 = imeis;
                    return new String[] {
                            imeis, TYPE_IMEI1
                    };
                }
            }
            if (isIMEIFormat(imei1) && isIMEIFormat(imei2)) {
                String smallerImei = imei1.compareTo(imei2) <= 0 ? imei1 : imei2;
                sIMEI1 = smallerImei;
                sIMEI2 = sIMEI1 == imei1 ? imei2 : imei1;
                return new String[] {
                        smallerImei, TYPE_IMEI1
                };
            }
        }
        List<String> imeiList = getImeiList(context);
        if (imeiList != null && imeiList.size() > 0) {
            return new String[] {
                    imeiList.get(0), TYPE_IMEI1
            };
        }
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String originalDeviceId = tm.getDeviceId();
        String retType = TYPE_UNKOWN;
        if (originalDeviceId != null && originalDeviceId.matches("^0*$")) {
            originalDeviceId = null;
        }
        if (isMEIDFormat(originalDeviceId)) {
            retType = TYPE_MEID;
        }
        return new String[] {
                originalDeviceId, retType
        };
    }

    /*
     * Returns all the IMEI, maybe null <p>Requires Permission: {@link
     * android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     */
    @SuppressLint("MissingPermission")
    public static List<String> getImeiList(Context context) {
        if (sGetImeiList != null && !isSupportCDMAForKK()) {
            try {
                List<String> imeiList = (List<String>) sGetImeiList.invoke(
                        sObjTelephonyManagerEx, null);
                if (imeiList != null && imeiList.size() > 0 && !hasInvalidIMEI(imeiList)) {
                    Collections.sort(imeiList);
                    return imeiList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (sGetImeiForSlot != null) {
                try {
                    TelephonyManager tm = (TelephonyManager) context
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    List<String> imeiList = new ArrayList<String>();
                    String imei1 = (String) sGetImeiForSlot.invoke(tm, 0);
                    if (isIMEIFormat(imei1)) {
                        if (!isMultiSim()) {
                            imeiList.add(imei1);
                            return imeiList;
                        }
                        String imei2 = (String) sGetImeiForSlot.invoke(tm, 1);
                        if (isIMEIFormat(imei2)) {
                            if (imei1.compareTo(imei2) > 0) {
                                imeiList.add(imei2);
                                imeiList.add(imei1);
                            } else {
                                imeiList.add(imei1);
                                imeiList.add(imei2);
                            }
                            return imeiList;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!isMultiSim() && sIMEI1 != null) {
                List<String> imeiList = new ArrayList<String>();
                imeiList.add(sIMEI1);
                return imeiList;
            }
            if (sIMEI1 != null && sIMEI2 != null) {
                List<String> imeiList = new ArrayList<String>();
                imeiList.add(sIMEI1);
                imeiList.add(sIMEI2);
                return imeiList;
            }
        } else if (Build.VERSION.SDK_INT < 21) {
            try {
                List<String> imeiList = new ArrayList<String>();
                Class clsTelephonyManager = Class.forName("android.telephony.TelephonyManager");
                if (!isMultiSim()) {
                    String deviceId = ((TelephonyManager) clsTelephonyManager.getMethod(
                            "getDefault").invoke(null)).getDeviceId();
                    if (isIMEIFormat(deviceId)) {
                        imeiList.add(deviceId);
                        return imeiList;
                    } else if (sIMEI1 != null) {
                        imeiList.add(sIMEI1);
                        return imeiList;
                    }
                } else {
                    String imei1 = ((TelephonyManager) clsTelephonyManager.getMethod("getDefault",
                            int.class)
                            .invoke(null, 0)).getDeviceId();
                    String imei2 = ((TelephonyManager) clsTelephonyManager.getMethod("getDefault",
                            int.class)
                            .invoke(null, 1)).getDeviceId();
                    String modDevice = getProp("ro.product.mod_device");
                    if (modDevice == null || !modDevice.contains("_global")) {// KK国内版只有１个IMEI，返回２个一样的
                        if (isIMEIFormat(imei1)) {
                            imeiList.add(imei1);
                            imeiList.add(imei1);
                        } else if (isIMEIFormat(imei2)) {
                            imeiList.add(imei2);
                            imeiList.add(imei2);
                        } else if (sIMEI1 != null) {
                            imeiList.add(sIMEI1);
                            imeiList.add(sIMEI1);
                        }
                    } else {
                        if (isIMEIFormat(imei1) && isIMEIFormat(imei2)) {
                            imeiList.add(imei1);
                            imeiList.add(imei2);
                        } else if (sIMEI1 != null && sIMEI2 != null) {
                            imeiList.add(sIMEI1);
                            imeiList.add(sIMEI2);
                        }
                    }
                    if (imeiList.size() > 0) {
                        return imeiList;
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    /*
     * Returns all the MEID, maybe null <p>Requires Permission: {@link
     * android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     */
    @SuppressLint("MissingPermission")
    public static List<String> getMeidList(Context context) {
        if (Build.VERSION.SDK_INT < 21 && isMultiSim()) {
            try {
                List<String> meidList = new ArrayList<String>();
                Class clsTelephonyManager = Class.forName("android.telephony.TelephonyManager");
                String deviceId1 = ((TelephonyManager) clsTelephonyManager.getMethod("getDefault",
                        int.class)
                        .invoke(null, 0)).getDeviceId();
                if (isMEIDFormat(deviceId1)) {
                    meidList.add(deviceId1);
                    return meidList;
                }
                 String deviceId2 = ((TelephonyManager) clsTelephonyManager.getMethod("getDefault",
                        int.class)
                        .invoke(null, 1)).getDeviceId();
                if (isMEIDFormat(deviceId2)) {
                    meidList.add(deviceId2);
                    return meidList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (sGetMeidList != null) {
            try {
                List<String> meidList = (List<String>) sGetMeidList.invoke(
                        sObjTelephonyManagerEx, null);
                if (meidList != null && meidList.size() > 0 && !hasInvalidMEID(meidList)) {
                    Collections.sort(meidList);
                    return meidList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String meid = tm.getDeviceId();
            if (!isMEIDFormat(meid)) {
                meid = getProp("persist.radio.meid");
                if (!isMEIDFormat(meid)) {
                    meid = getProp("ro.ril.oem.meid");
                }
            }
            if (isMEIDFormat(meid)) {
                List<String> meidList = new ArrayList<String>();
                meidList.add(meid);
                return meidList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isMultiSim() {
        if ("dsds".equals(getProp("persist.radio.multisim.config"))) {
            return true;
        }
        String device = Build.DEVICE;
        if ("lcsh92_wet_jb9".equals(device) || "lcsh92_wet_tdd".equals(device) // redmi3
                || "HM2013022".equals(device) || "HM2013023".equals(device)
                || "armani".equals(device)
                || "HM2014011".equals(device) || "HM2014012".equals(device)// redmi2
                ) {
            return true;
        }
        return false;
    }

    private static String getProp(String propKey) {
        try {
            if (sGetProp != null) {
                return String.valueOf(sGetProp.invoke(null, propKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isSupportCDMAForKK() {
        if (Build.VERSION.SDK_INT >= 21) { // only for that elder than KK
            return false;
        }
        String device = Build.DEVICE;
        String modemProp = getProp("persist.radio.modem");
        if ("HM2014812".equals(device) || "HM2014821".equals(device) // H2XCT
                || ("gucci".equals(device) && "ct".equals(getProp("persist.sys.modem"))) // H3XCT
                || "CDMA".equals(modemProp) // X2,X4 CT
                || "HM1AC".equals(modemProp) // H2AC
                || "LTE-X5-ALL".equals(modemProp) // X5 CT
                || "LTE-CT".equals(modemProp) // X4 CT-LTE
                || "MI 3C".equals(Build.MODEL)) {
            return true;
        }
        return false;
    }

    private static boolean hasInvalidIMEI(List<String> imeiList) {
        for (String imei : imeiList) {
            if (!isIMEIFormat(imei)) {// imei check
                return true;
            }
        }
        return false;
    }

    private static boolean hasInvalidMEID(List<String> meidList) {
        for (String meid : meidList) {
            if (!isMEIDFormat(meid)) {// meid check
                return true;
            }
        }
        return false;
    }

    private static boolean isIMEIFormat(String imei) {
        return imei != null && imei.length() == IMEI_LENGTH && !imei.matches("^0*$");
    }

    private static boolean isMEIDFormat(String meid) {
        return meid != null && meid.length() == MEID_LENGTH && !meid.matches("^0*$");
    }
}