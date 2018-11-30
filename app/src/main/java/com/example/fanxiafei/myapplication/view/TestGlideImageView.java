package com.example.fanxiafei.myapplication.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

public class TestGlideImageView extends android.support.v7.widget.AppCompatImageView {

    private ResourceReadyListener listener;
    private long timestamp;

    public TestGlideImageView(Context context) {
        super(context);
    }

    public TestGlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setResourceReadyListener(long timestamp, ResourceReadyListener listener) {
        this.timestamp = timestamp;
        this.listener = listener;
    }


    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        Log.d("TestGlideImageView", "setImageDrawable: "+drawable);
        if (listener != null && drawable != null) {
            listener.onResourceReady(timestamp);
        }

    }

    public interface ResourceReadyListener {
        void onResourceReady(long timestamp);
    }
}
