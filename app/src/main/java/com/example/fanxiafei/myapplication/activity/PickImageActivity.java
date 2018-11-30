package com.example.fanxiafei.myapplication.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.model.Image;
import com.example.fanxiafei.myapplication.model.ImageModel;
import com.example.fanxiafei.myapplication.view.ImageAdaptor;

import java.util.ArrayList;

public class PickImageActivity extends Activity {
    private static final String TAG = PickImageActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ImageAdaptor adaptor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        adaptor = new ImageAdaptor();
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adaptor);
        loadImageForSDCard();
    }

    private void loadImageForSDCard() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            goToPick();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(PickImageActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private void goToPick() {
        ImageModel.loadImageForSDCard(this, new ImageModel.DataCallback() {
            @Override
            public void onSuccess(ArrayList<Image> folders) {

                Log.d(TAG, "onSuccess: ");
                adaptor.setImageList(folders);
                adaptor.notifyDataSetChanged();
//                recyclerView.addItemDecoration(new CommonRecycleViewDivider(CommonRecycleViewDivider.GRID, 0, ScreenUtils.dp2px(PickImageActivity.this, 7), 0xff000000));
            }

            @Override
            public void onFail() {
                Log.d(TAG, "onFail: ");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
                //有权限，加载图片。
                goToPick();
            }
        }
    }
}
