package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.fanxiafei.myapplication.GlideApp;
import com.example.fanxiafei.myapplication.R;
import com.example.fanxiafei.myapplication.view.TestGlideImageView;

public class GlideImageTestActivityB extends Activity {

    private String url1 = "http://t3.market.mi-img.com/thumbnail/webp/h0/MiddleReview/00ac3e4b0f7544d7620e7ad8b90ce47ba41fd6a29";
    private String url2 = "http://t5.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/0ea9485aa24fa453d1eb5d9c6100d3d2a6df530ec";
    private String url3 = "http://t3.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/0bac3e4b0f7244d7b20e72d8b9fce472a41fc6a29";
    private String url4 = "http://t2.market.mi-img.com/thumbnail/webp/h0/MiddleReview/07b185f1f2cb3164e053af513fc089244b2426a70";
    private String url5 = "http://t2.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/0b281a5a7302649252bd488d92f6142d7da616ff6";
    private String url6 = "http://t4.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/0c66553d37063ffd85b13f129b55249f4ba41760d";
    private String url7 = "http://t3.market.mi-img.com/thumbnail/webp/h0/MiddleReview/0cf6149f6c1acbfe71d19b220c726155f43436fed";
    private String url8 = "http://t2.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/02159f459f0ab471b0a307fc37d53c78e7f5c15d3";
    private String url9 = "http://t2.market.mi-img.com/thumbnail/webp/h0/MiddleReview/06e964f195f940f9835c5cf192e2b6e9e20410998";
    private String url10 = "http://t2.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/01e9684190f544f9015c55f199e2b9e1ef983e906";
    private String url11 = "http://t3.market.mi-img.com/thumbnail/webp/h0/MiddleReview/0d40544ecaddd5b4e30dc5c1f0d87839f5b41a8bf";
    private String url12 = "http://t4.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/008594f835cf462b1ec5e1e0590a7d952474332cd";
    private String url13 = "http://t5.market.xiaomi.com/thumbnail/webp/h0/MiddleReview/088ab14c268e049891bc5cd4ef930d6af86940d08";
    private long totalTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_b);
        TestGlideImageView testGlideImageView1 = findViewById(R.id.image_view_1);
        TestGlideImageView testGlideImageView2 = findViewById(R.id.image_view_2);
        TestGlideImageView testGlideImageView3 = findViewById(R.id.image_view_3);
        TestGlideImageView testGlideImageView4 = findViewById(R.id.image_view_4);
        TestGlideImageView testGlideImageView5 = findViewById(R.id.image_view_5);
        TestGlideImageView testGlideImageView6 = findViewById(R.id.image_view_6);
        TestGlideImageView testGlideImageView7 = findViewById(R.id.image_view_7);
        TestGlideImageView testGlideImageView8 = findViewById(R.id.image_view_8);
        TestGlideImageView testGlideImageView9 = findViewById(R.id.image_view_9);
        TestGlideImageView testGlideImageView10 = findViewById(R.id.image_view_10);
        TestGlideImageView testGlideImageView11 = findViewById(R.id.image_view_11);
        TestGlideImageView testGlideImageView12 = findViewById(R.id.image_view_12);
        TestGlideImageView testGlideImageView13 = findViewById(R.id.image_view_13);

        loadImageSource(url1, testGlideImageView1);
        loadImageSource(url2, testGlideImageView2);
        loadImageSource(url3, testGlideImageView3);
        loadImageSource(url4, testGlideImageView4);
        loadImageSource(url5, testGlideImageView5);
        loadImageSource(url6, testGlideImageView6);
        loadImageSource(url7, testGlideImageView7);
        loadImageSource(url8, testGlideImageView8);
        loadImageSource(url9, testGlideImageView9);
        loadImageSource(url10, testGlideImageView10);
        loadImageSource(url11, testGlideImageView11);
        loadImageSource(url12, testGlideImageView12);
        loadImageSource(url13, testGlideImageView13);
    }

    private void loadImageSource(String url, TestGlideImageView testGlideImageView) {
        GlideApp.with(this)
                .load(url)
                .into(testGlideImageView);
        testGlideImageView.setResourceReadyListener(System.currentTimeMillis(), startTime -> {
            long now = System.currentTimeMillis();
            long spend = (now - startTime);
            totalTime += spend;
            Log.d("sofina", "GlideImageTestActivityB: spend = " + spend + "  total = " + totalTime);
        });
    }
}
