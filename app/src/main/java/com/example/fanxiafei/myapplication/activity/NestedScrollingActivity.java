package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.model.Image;
import com.example.fanxiafei.myapplication.model.ImageModel;
import com.example.fanxiafei.myapplication.view.ImageAdaptor;
import com.example.fanxiafei.myapplication.view.MyWebView;

import java.util.ArrayList;

public class NestedScrollingActivity extends Activity {

    private NestedScrollView nestedScrollView;
    private MyWebView webView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling_layout);
        initView();
        initData();

    }
    private void initView() {
        nestedScrollView = findViewById(R.id.parent);
        webView = findViewById(R.id.web_view);
        recyclerView = findViewById(R.id.recycler_view);
    }
    private void initData() {
        webView.loadUrl("https://www.jianshu.com/p/f55abc60a879");
        webView.setNestedScrollingEnabled(false);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ImageAdaptor adaptor = new ImageAdaptor();
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adaptor);
        ImageModel.loadImageForSDCard(this, new ImageModel.DataCallback() {
            @Override
            public void onSuccess(ArrayList<Image> folders) {
                adaptor.setImageList(folders);
                adaptor.notifyDataSetChanged();
            }

            @Override
            public void onFail() {
            }
        });

    }
}
