package com.example.fanxiafei.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.utils.JsonUtils;

import junit.framework.Assert;

import java.util.Observable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"VisibleForTests", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout root = findViewById(R.id.root_layout);
        listActivities(root);
        JsonUtils.toJson();
        boolean isLog = Log.isLoggable("MemorySizeCalculator", Log.DEBUG);
        Log.d("MainActivity", "onCreate: "+isLog);
    }

    private void listActivities(LinearLayout root) {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            final ActivityInfo[] activityInfos = packageInfo.activities;
            Assert.assertNotNull(activityInfos);

            for (ActivityInfo activityInfo : activityInfos) {
                Assert.assertNotNull(activityInfo);

                if(!mainActivity(activityInfo)) {
                    root.addView(getNewButton(activityInfo), getBtnParams());
                }
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean mainActivity(ActivityInfo activityInfo) {
        return activityInfo.name.equals(getClass().getName());
    }

    private Button getNewButton(ActivityInfo activityInfo) throws ClassNotFoundException {
        final Class<?> cls = Class.forName(activityInfo.name);
        String name = activityInfo.name.substring(activityInfo.name.lastIndexOf(".") + 1);

        Button button = new Button(this);
        button.setText(name);
        button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, cls)));
        return button;
    }

    private LinearLayout.LayoutParams getBtnParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 20;
        return params;
    }
}

