package com.example.fanxiafei.myapplication.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

public class MyTranslationAnimation extends TranslateAnimation {

    AnimationUpdateListener listener;

    public MyTranslationAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTranslationAnimation(Context context, AnimationUpdateListener listener) {
        super(context, null);
        this.listener = listener;
        init();
    }

    public MyTranslationAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
        super(fromXDelta, toXDelta, fromYDelta, toYDelta);
        init();
    }

    public MyTranslationAnimation(int fromXType, float fromXValue, int toXType, float toXValue, int fromYType, float fromYValue, int toYType, float toYValue) {
        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        init();

    }

    private void init() {
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if(listener != null) {
                    listener.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(listener != null) {
                    listener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(listener != null) {
                    listener.onAnimationRepeat();
                }
            }
        });
    }

    public void setAnimationUpdateListenrAdapter(AnimationUpdateListener listenrAdapter){
        this.listener = listenrAdapter;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        Log.d("sofina", "applyTransformation: "+interpolatedTime);
        if(listener != null) {
            listener.onAnimationUpdate(interpolatedTime);
        }
    }
}
