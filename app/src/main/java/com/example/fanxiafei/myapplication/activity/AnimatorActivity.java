package com.example.fanxiafei.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import com.example.fanxiafei.myapplication.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnimatorActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animator_activity);
        get();
    }

    AnimationFrameCallback callback = new AnimationFrameCallback() {
        @Override
        public boolean doAnimationFrame(long frameTime) {
            Log.d("sofina", "doFrame: ");
            return false;
        }

        @Override
        public void commitAnimationFrame(long frameTime) {
            Log.d("sofina", "doFrame: ");

        }
    };

    private void get() {
        try {
            Class clazz = Class.forName("android.animation.AnimationHandler");
            Field field = clazz.getField("mAnimationCallbacks");
            field.setAccessible(true);
            Log.d("sofina", "get: "+field);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    ObjectAnimator objectAnimator;

    public void add(View v) {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1f, 2f);
//            objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//            objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d("sofina", "onAnimationUpdate: ");
                }
            });
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Log.d("sofina", "onAnimationEnd: ");
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    Log.d("sofina", "onAnimationStart: ");
                }
            });
            objectAnimator.setDuration(5000);
        }
//        objectAnimator.setAutoCancel(true);
        objectAnimator.start();
        Log.d("sofinaa", "start: ");
    }

    public void remove(View v) {
//        removeCallback(callback);
//        objectAnimator.setRepeatCount(0);
        Log.d("sofinaa", "remove: ");
        objectAnimator.end();
    }

    private final ArrayList<AnimationFrameCallback> mAnimationCallbacks =
            new ArrayList<>();
    private AnimationFrameCallbackProvider mProvider;

    private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            final int size = mAnimationCallbacks.size();
            for (int i = 0; i < size; i++) {
                AnimationFrameCallback callback = mAnimationCallbacks.get(i);

            }
            if (mAnimationCallbacks.size() > 0) {
                getProvider().postFrameCallback(this);
            }

        }
    };

    /**
     * Register to get a callback on the next frame after the delay.
     */
    public void addAnimationFrameCallback(final AnimationFrameCallback callback, long delay) {
        if (mAnimationCallbacks.size() == 0) {
            getProvider().postFrameCallback(mFrameCallback);
        }
        if (!mAnimationCallbacks.contains(callback)) {
            mAnimationCallbacks.add(callback);
        }

    }

    /**
     * Removes the given callback from the list, so it will no longer be called for frame related
     * timing.
     */
    public void removeCallback(AnimationFrameCallback callback) {
        int id = mAnimationCallbacks.indexOf(callback);
        if (id >= 0) {
            Log.d("sofina", "removeCallback: ");
            mAnimationCallbacks.set(id, null);
        }
    }


    private AnimationFrameCallbackProvider getProvider() {
        if (mProvider == null) {
            mProvider = new MyFrameCallbackProvider();
        }
        return mProvider;
    }


    interface AnimationFrameCallback {
        /**
         * Run animation based on the frame time.
         *
         * @param frameTime The frame start time, in the {@link SystemClock#uptimeMillis()} time
         *                  base.
         * @return if the animation has finished.
         */
        boolean doAnimationFrame(long frameTime);

        /**
         * This notifies the callback of frame commit time. Frame commit time is the time after
         * traversals happen, as opposed to the normal animation frame time that is before
         * traversals. This is used to compensate expensive traversals that happen as the
         * animation starts. When traversals take a long time to complete, the rendering of the
         * initial frame will be delayed (by a long time). But since the startTime of the
         * animation is set before the traversal, by the time of next frame, a lot of time would
         * have passed since startTime was set, the animation will consequently skip a few frames
         * to respect the new frameTime. By having the commit time, we can adjust the start time to
         * when the first frame was drawn (after any expensive traversals) so that no frames
         * will be skipped.
         *
         * @param frameTime The frame time after traversals happen, if any, in the
         *                  {@link SystemClock#uptimeMillis()} time base.
         */
        void commitAnimationFrame(long frameTime);
    }

    /**
     * Default provider of timing pulse that uses Choreographer for frame callbacks.
     */
    private class MyFrameCallbackProvider implements AnimationFrameCallbackProvider {

        final Choreographer mChoreographer = Choreographer.getInstance();

        @Override
        public void postFrameCallback(Choreographer.FrameCallback callback) {
            mChoreographer.postFrameCallback(callback);
        }

        @Override
        public void postCommitCallback(Runnable runnable) {
//            mChoreographer.postCallback(Choreographer.CALLBACK_COMMIT, runnable, null);
        }

        @Override
        public long getFrameTime() {
//            return mChoreographer.getFrameTime();
            return System.currentTimeMillis();
        }

        @Override
        public long getFrameDelay() {
//            return Choreographer.getFrameDelay();
            return 50;
        }

        @Override
        public void setFrameDelay(long delay) {
//            Choreographer.setFrameDelay(delay);
        }
    }

    public interface AnimationFrameCallbackProvider {
        void postFrameCallback(Choreographer.FrameCallback callback);

        void postCommitCallback(Runnable runnable);

        long getFrameTime();

        long getFrameDelay();

        void setFrameDelay(long delay);
    }

}
