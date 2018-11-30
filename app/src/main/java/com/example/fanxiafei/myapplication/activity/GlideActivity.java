package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.fanxiafei.myapplication.GlideApp;
import com.example.fanxiafei.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class GlideActivity extends Activity {

    private static final String TAG = GlideActivity.class.getSimpleName();

    private String url = "http://t3.market.mi-img.com/thumbnail/webp/w495h495/MiddleReview/00ac3e4b0f7544d7620e7ad8b90ce47ba41fd6a29";

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        imageView = findViewById(R.id.image_view);
        showLoopAnimation();

//        showGif();
    }

    private void showLoopAnimation() {
        GlideApp.with(this)
                .asGif()
                .load(url)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        if (resource instanceof GifDrawable) {
                            ((GifDrawable) resource).start();
                            ((GifDrawable) resource).setLoopCount(1);
                        }
                        imageView.setImageDrawable(resource);
                    }
                });

    }

    private Handler handler = new Handler();
    private GifDrawable gifDrawable;

    private Runnable loop = new Runnable() {
        @Override
        public void run() {
            gifDrawable.start();
            gifDrawable.setLoopCount(1);
            imageView.setImageDrawable(gifDrawable);
            handler.postDelayed(this, 5 * 1000);
        }
    };


    private void showGif() {
        Glide.with(this)
                .asGif()
                .apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig())
                .load(url)
                .into(new SimpleTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        gifDrawable = resource;
                        handler.post(loop);
                    }
                });
    }


}

