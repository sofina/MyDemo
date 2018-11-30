package com.example.fanxiafei.myapplication.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class GsonUtil {

    private static Gson gsonInstance = new Gson();

    public static Gson getGson() {
        return gsonInstance;
    }

    public static String toJson(Object object) {
        return gsonInstance.toJson(object);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return gsonInstance.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gsonInstance.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gsonInstance.fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) {
        return gsonInstance.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return gsonInstance.fromJson(json, classOfT);
    }

}
