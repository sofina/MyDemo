package com.example.fanxiafei.myapplication.utils;

public class ReflectUtils {

    public static String TAG = "ReflectUtils";

    public static void main(){
//        Son son = new Son();
//        Class clazz = son.getClass();
//        Log.d(TAG, "main: clazz = "+clazz);
//        Log.d(TAG, "main: clazz getGenericSuperclass= "+clazz.getGenericSuperclass());
//        Log.d(TAG, "main: clazz getSuperclass= "+clazz.getSuperclass());
//
//        Type type= clazz.getGenericSuperclass();
//        Log.d(TAG, "main: clazz type= "+type);
//        ParameterizedType p = (ParameterizedType) type;
//        Log.d(TAG, "main: clazz p= "+p);
//        Class c1 = (Class) p.getActualTypeArguments()[0];
//        Class c2 = (Class) p.getActualTypeArguments()[1];
//        Log.d(TAG, "main: clazz c1= "+c1);
//        Log.d(TAG, "main: clazz c2= "+c2);
    }

    public static class Father<T1,T2>{

    }

    public abstract class ViewObject<T extends Object>{
        public abstract void onBindViewHolder(T viewHolder);

    }

    public  class Son extends ViewObject< Boolean>{


        @Override
        public void onBindViewHolder(Boolean viewHolder) {

        }
    }


}
