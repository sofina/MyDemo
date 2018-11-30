package com.example.fanxiafei.myapplication.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.webkit.WebView;

import static android.support.v4.view.ViewCompat.TYPE_TOUCH;

public class MyWebView extends WebView implements NestedScrollingChild2 {

    private static final String TAG = "MyWebView";
    private NestedScrollingChildHelper mScrollingChildHelper;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG, "onScrollChanged: ");
    }

    private VelocityTracker mVelocityTracker;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];
    private int mScrollState = SCROLL_STATE_IDLE;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mTouchSlop;

    private static final int INVALID_POINTER = -1;

    /**
     * The RecyclerView is not currently scrolling.
     */
    public static final int SCROLL_STATE_IDLE = 0;
    private boolean isScrollX = false;

//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (MotionEventCompat.getPointerCount(event) == 1) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    isScrollX = false;
//                    getParent().getParent()
//                            .requestDisallowInterceptTouchEvent(true);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    getParent().getParent()
//                            .requestDisallowInterceptTouchEvent(!isScrollX);
//                    break;
//                default:
//                    getParent().getParent()
//                            .requestDisallowInterceptTouchEvent(false);
//            }
//        } else {
//            //使webview可以双指缩放（前提是webview必须开启缩放功能，并且加载的网页也支持缩放）
//            getParent().getParent().
//                    requestDisallowInterceptTouchEvent(true);
//        }
//        return super.onTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;

        MotionEvent trackedEvent = MotionEvent.obtain(event);

        final int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_DOWN) {
            mNestedOffsets[1] = 0;
        }

        int y = (int) event.getY();

        event.offsetLocation(0, mNestedOffsets[1]);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isScrollX = false;
                mLastTouchY = y;
                getParent().getParent()
                        .requestDisallowInterceptTouchEvent(true);
                result = super.onTouchEvent(event);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().getParent()
                        .requestDisallowInterceptTouchEvent(!isScrollX);

                int deltaY = mLastTouchY - y;

                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1];
                    trackedEvent.offsetLocation(0, mScrollOffset[1]);
                    mNestedOffsets[1] += mScrollOffset[1];
                }

                mLastTouchY = y - mScrollOffset[1];

                int oldY = getScrollY();
                int newScrollY = Math.max(0, oldY + deltaY);
                int dyConsumed = newScrollY - oldY;
                int dyUnconsumed = deltaY - dyConsumed;
                result = super.onTouchEvent(trackedEvent);
                trackedEvent.recycle();

                if (dispatchNestedScroll(0, dyConsumed, 0, dyUnconsumed, mScrollOffset)) {
                    mLastTouchY -= mScrollOffset[1];
                    trackedEvent.offsetLocation(0, mScrollOffset[1]);
                    mNestedOffsets[1] += mScrollOffset[1];
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().getParent()
                        .requestDisallowInterceptTouchEvent(false);
                stopNestedScroll();
                result = super.onTouchEvent(event);
                break;
        }
        return result;
    }

    //当webview滚动到边界时执行
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        Log.d(TAG, "onOverScrolled: " + scrollX + "   " + scrollY + "   " + clampedX + "   " + clampedY);
        isScrollX = clampedY;
        setNestedScrollingEnabled(clampedY);
    }


    private void onPointerUp(MotionEvent e) {
        final int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = e.getPointerId(newIndex);
            mInitialTouchX = mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
            mInitialTouchY = mLastTouchY = (int) (e.getY(newIndex) + 0.5f);
        }
    }


    // NestedScrollingChild

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return getScrollingChildHelper().startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public void stopNestedScroll(int type) {
        getScrollingChildHelper().stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return getScrollingChildHelper().hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow, int type) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow,
                                           int type) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow,
                type);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }
}
