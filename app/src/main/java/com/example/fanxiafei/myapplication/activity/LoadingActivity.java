package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.example.fanxiafei.myapplication.loading.CircleLoadingView;
import com.example.fanxiafei.myapplication.loading.ProgressImage;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressImage image = new ProgressImage(this);
        addContentView(image, new FrameLayout.LayoutParams(1000, 1000));

    }
}
