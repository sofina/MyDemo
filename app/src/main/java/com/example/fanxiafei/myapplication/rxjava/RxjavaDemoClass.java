package com.example.fanxiafei.myapplication.rxjava;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.fanxiafei.myapplication.log.Logger;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.fanxiafei.myapplication.utils.ReflectUtils.TAG;

public class RxjavaDemoClass {

    public static void main(String args[]) {
//        Observable.create((ObservableOnSubscribe<Integer>) e -> {
//            e.onNext(1);
//            e.onComplete();
//
//        }).subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new Observer<Integer>() {
//
//                    //初始化observer
//                    private int i;
//                    private Disposable disposable;
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        i++;
//                        if (i == 1) {
//                            disposable.dispose();//切断
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
        demo();

    }

    @SuppressLint("CheckResult")
    public static void demo() {
        Observable.create((ObservableOnSubscribe<Response>) e -> {
            Request.Builder builder = new Request.Builder()
                    .url("http://api.avatardata.cn/MobilePlace/LookUp?key=ec47b85086be4dc8b5d941f5abd37a4e&mobileNumber=13021671512")
                    .get();
            Request request = builder.build();
            Call call = new OkHttpClient().newCall(request);
            Response response = call.execute();
            Log.d(TAG, "create: response = " + response);
            e.onNext(response);
            e.onComplete();
        }).map(response -> {
            Log.d(TAG, "map: response = " + response);
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                String json = new Gson().toJson(body);
                return json;
            }
            return null;

        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(o -> Logger.d_2json(TAG, o))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> Log.d(TAG, "subscribe accept: o = " + o), throwable -> Log.d(TAG, "Throwable accept: throwable = " + throwable));

    }

    private static List<String> mList;

    private static Disposable disposable;

    @SuppressLint("CheckResult")
    public static void demo1() {
        disposable = Flowable.interval(1, TimeUnit.SECONDS)
                .doOnNext(aLong -> Log.e(TAG, "accept: doOnNext : " + aLong))
                .subscribe(aLong -> Log.e(TAG, "accept: subscribe : " + aLong));
    }

    public static void release() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

}

