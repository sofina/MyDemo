package com.example.fanxiafei.myapplication.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyLayout extends LinearLayout {

    private ViewDragHelper viewDragHelper;

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("sofina", "MyLayout: ");
        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                return top;
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
                Toast.makeText(getContext(), "edgeTouched", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                viewDragHelper.settleCapturedViewAt(0, 0);
                invalidate();
            }

        });
        viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        Log.d("sofina", "dispatchHoverEvent: ");
        return super.dispatchHoverEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("sofina", "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean should = viewDragHelper.shouldInterceptTouchEvent(event);
        Log.d("sofina", "onInterceptHoverEvent: should = "+should);
        return should;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("sofina", "onTouchEvent: ");
        viewDragHelper.processTouchEvent(event);
        return true;
    }

}
