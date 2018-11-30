package com.example.fanxiafei.myapplication.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class CircleLoadingView extends View {

    private Paint yellowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float radius = 200;
    private float distance = 200;

    private float p1yellowX, p2yellowX, p3yellowX, p4yellowX;
    private float p1yellowY, p2yellowY, p3yellowY, p4yellowY;
    private float c1yellowX, c2yellowX, c3yellowX, c4yellowX, c5yellowX, c6yellowX, c7yellowX, c8yellowX;
    private float c1yellowY, c2yellowY, c3yellowY, c4yellowY, c5yellowY, c6yellowY, c7yellowY, c8yellowY;
    private float m1YellowX, m2YellowX, m3YellowX, m4YellowX, m1cYellowX, m2cYellowX, m3cYellowX, m4cYellowX, m5cYellowX, m6cYellowX, m7cYellowX, m8cYellowX;
    private float m1cYellowY, m8cYellowY, m2cYellowY, m7cYellowY;

    private float p1RedX, p2RedX, p3RedX, p4RedX;
    private float p1RedY, p2RedY, p3RedY, p4RedY;
    private float c1RedX, c2RedX, c3RedX, c4RedX, c5RedX, c6RedX, c7RedX, c8RedX;
    private float c1RedY, c2RedY, c3RedY, c4RedY, c5RedY, c6RedY, c7RedY, c8RedY;

    private float m1RedX, m2RedX, m3RedX, m4RedX, m1cRedX, m2cRedX, m3cRedX, m4cRedX, m5cRedX, m6cRedX, m7cRedX, m8cRedX;

    private float magic = 0.552284749831f;
    private Path pathYellow;
    private Path pathRed;

    public CircleLoadingView(Context context) {
        super(context);
        initView();
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        yellowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        yellowPaint.setColor(Color.YELLOW);

        redPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(4);

        pathYellow = new Path();
        pathRed = new Path();

        initCircle();
        initPoint();
        setUpAnimator();
    }

    private void initPoint() {
        m1RedX = m1YellowX = p1yellowX;
        m2RedX = m2YellowX = p2yellowX;
        m3RedX = m3YellowX = p3yellowX;
        m4RedX = m4YellowX = p4yellowX;
        m1cRedX = m1cYellowX = c1yellowX;
        m2cRedX = m2cYellowX = c2yellowX;
        m3cRedX = m3cYellowX = c3yellowX;
        m4cRedX = m4cYellowX = c4yellowX;
        m5cRedX = m5cYellowX = c5yellowX;
        m6cRedX = m6cYellowX = c6yellowX;
        m7cRedX = m7cYellowX = c7yellowX;
        m8cRedX = m8cYellowX = c8yellowX;
        m1cYellowY = c1yellowY;
        m8cYellowY = c8yellowY;
        m2cYellowY = c2yellowY;
        m7cYellowY = c7yellowY;
    }

    private void initCircle() {
        p1RedX = p1yellowX = radius;
        p1RedY = p1yellowY = 0;

        p2RedX = p2yellowX = 0;
        p2RedY = p2yellowY = radius;

        p3RedX = p3yellowX = -radius;
        p3RedY = p3yellowY = 0;

        p4RedX = p4yellowX = 0;
        p4RedY = p4yellowY = -radius;

        c1RedX = c1yellowX = radius;
        c1RedY = c1yellowY = magic * radius;

        c2RedX = c2yellowX = magic * radius;
        c2RedY = c2yellowY = radius;

        c3RedX = c3yellowX = -magic * radius;
        c3RedY = c3yellowY = radius;

        c4RedX = c4yellowX = -radius;
        c4RedY = c4yellowY = magic * radius;

        c5RedX = c5yellowX = -radius;
        c5RedY = c5yellowY = -magic * radius;

        c6RedX = c6yellowX = -magic * radius;
        c6RedY = c6yellowY = -radius;

        c7RedX = c7yellowX = magic * radius;
        c7RedY = c7yellowY = -radius;

        c8RedX = c8yellowX = radius;
        c8RedY = c8yellowY = -magic * radius;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (animator != null) {
            if (hasWindowFocus) {
                animator.resume();
            } else {
                animator.pause();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(500, 500);

        int count0 = canvas.save();
        canvas.translate(-distance / 2 - radius, 0);
        pathYellow.rewind();
        pathYellow.moveTo(m1YellowX, p1yellowY);
        pathYellow.cubicTo(m1cYellowX, m1cYellowY, m2cYellowX, m2cYellowY, m2YellowX, p2yellowY);
        pathYellow.cubicTo(m3cYellowX, c3yellowY, m4cYellowX, c4yellowY, m3YellowX, p3yellowY);
        pathYellow.cubicTo(m5cYellowX, c5yellowY, m6cYellowX, c6yellowY, m4YellowX, p4yellowY);
        pathYellow.cubicTo(m7cYellowX, m7cYellowY, m8cYellowX, m8cYellowY, m1YellowX, p1yellowY);
        canvas.drawPath(pathYellow, yellowPaint);



        Log.d("sofina", "onDraw: m1cYellowX = " + m1cYellowX + "   m1cYellowY = " + m1cYellowY + "   m8cYellowY = " + m8cYellowY + "   m1YellowX = " + m1YellowX);

//        int count1 = canvas.save();
//        canvas.translate(+distance / 2 + radius, 0);
//        pathRed.rewind();
//        pathRed.moveTo(m1RedX, p1RedY);
//        pathRed.cubicTo(m1cRedX, c1RedY, m2cRedX, c2RedY, m2RedX, p2RedY);
//        pathRed.cubicTo(m3cRedX, c3RedY, m4cRedX, c4RedY, m3RedX, p3RedY);
//        pathRed.cubicTo(m5cRedX, c5RedY, m6cRedX, c6RedY, m4RedX, p4RedY);
//        pathRed.cubicTo(m7cRedX, c7RedY, m8cRedX, c8RedY, m1RedX, p1RedY);
//        canvas.drawPath(pathRed, redPaint);
//        canvas.restoreToCount(count1);

        canvas.drawPoint(m1YellowX, p1yellowY, redPaint);
        canvas.drawPoint(m1cYellowX, m1cYellowY, redPaint);
        canvas.drawPoint(m2cYellowX, c2yellowY, redPaint);
        canvas.drawPoint(m2YellowX, p2yellowY, redPaint);
        canvas.drawPoint(m3cYellowX, c3yellowY, redPaint);
        canvas.drawPoint(m4cYellowX, c4yellowY, redPaint);
        canvas.drawPoint(m3YellowX, p3yellowY, redPaint);
        canvas.drawPoint(m5cYellowX, c5yellowY, redPaint);
        canvas.drawPoint(m6cYellowX, c6yellowY, redPaint);
        canvas.drawPoint(m4YellowX, p4yellowY, redPaint);
        canvas.drawPoint(m7cYellowX, c7yellowY, redPaint);
        canvas.drawPoint(m8cYellowX, m8cYellowY, redPaint);
        canvas.drawPoint(m1YellowX, p1yellowY, redPaint);

        canvas.restoreToCount(count0);
    }

    private int toRight = 0;

    public void yellow2Right() {

        if (progress <= 0.3f) {
            float progress1 = 1.0f + ((progress - 0.3f) / 0.3f);
            m1YellowX = (p1yellowX + stage1Distance(progress1));
            m1cYellowX = m8cYellowX = (c1yellowX + stage1Distance(progress1));
            m2cYellowX = m7cYellowX = (c2yellowX + stage1Distance(progress1));
            m2YellowX = m4YellowX = (p2yellowX + stage1Distance(progress1));
            m3cYellowX = m6cYellowX = (c3yellowX + stage1Distance(progress1));
            m4cYellowX = m5cYellowX = (c4yellowX + stage1Distance(progress1));
            m3YellowX = (p3yellowX + +stage1Distance(progress1));
        } else if (progress <= 0.5f) {
            float progress2 = 1 + (progress - 0.5f) / 0.2f;
            m1YellowX = p1yellowX + stage6Distance(progress2);

            m1cYellowX = m8cYellowX = c1yellowX + stage6Distance(progress2);

            m1cYellowY = c1yellowY - stage7Distance(progress2) * magic * radius;
            m8cYellowY = c8yellowY + stage7Distance(progress2) * magic * radius;
            m2cYellowY = c2yellowY + stage7Distance(progress2) * magic * radius;
            m7cYellowY = c7yellowY + stage7Distance(progress2) * magic * radius;

            m2cYellowX = m7cYellowX = c2yellowX + stage6Distance(progress2);
            m2YellowX = m4YellowX = p2yellowX + stage6Distance(progress2);
            m3cYellowX = m6cYellowX = c3yellowX + stage6Distance(progress2);
            m4cYellowX = m5cYellowX = c4yellowX + stage6Distance(progress2);
            m3YellowX = p3yellowX + stage6Distance(progress2);

        } else if (progress <= 0.7f) {
//            float progress2 = 1 + (progress - 1f) / 0.7f;
//            m1YellowX = p1yellowX + stage5Distance(progress2);
//
//            m1cYellowX = m8cYellowX = c1yellowX + stage4Distance(progress2);
//            m2cYellowX = m7cYellowX = c2yellowX + stage4Distance(progress2);
//
//            m2YellowX = m4YellowX = p2yellowX + stage2Distance(progress2);
//            m3cYellowX = m6cYellowX = c3yellowX + stage2Distance(progress2);
//            m4cYellowX = m5cYellowX = c4yellowX + stage2Distance(progress2);
//            m3YellowX = p3yellowX + stage2Distance(progress2);
        }
    }

    private void red2Right() {
        if (progress <= 0.3f) {
            float progress1 = 1.0f + ((progress - 0.3f) / 0.3f);
            m1RedX = p1RedX + stage1Distance(progress1);
            m1cRedX = m8cRedX = c1RedX + stage1Distance(progress1);
            m2cRedX = m7cRedX = c2RedX + stage1Distance(progress1);
            m2RedX = m4RedX = p2RedX + stage1Distance(progress1);
            m3cRedX = m6cRedX = c3RedX + stage1Distance(progress1);
            m4cRedX = m5cRedX = c4RedX + stage1Distance(progress1);
            m3RedX = p3RedX + stage1Distance(progress1);
        } else {
            float progress2 = 1 + (progress - 1f) / 0.7f;
            m1RedX = p1RedX + stage5Distance(progress2);

            m1cRedX = m8cRedX = c1RedX + stage4Distance(progress2);
            m2cRedX = m7cRedX = c2RedX + stage4Distance(progress2);

            m2RedX = m4RedX = p2RedX + stage2Distance(progress2);
            m3cRedX = m6cRedX = c3RedX + stage2Distance(progress2);
            m4cRedX = m5cRedX = c4RedX + stage2Distance(progress2);
            m3RedX = p3RedX + stage2Distance(progress2);
        }
    }

    public void yellow2Left() {
        if (progress <= 0.3f) {
            float progress1 = 1.0f + ((progress - 0.3f) / 0.3f);
            m1YellowX = p1yellowX - stage1Distance(progress1);
            m1cYellowX = m8cYellowX = c1yellowX - stage1Distance(progress1);
            m2cYellowX = m7cYellowX = c2yellowX - stage1Distance(progress1);
            m2YellowX = m4YellowX = p2yellowX - stage1Distance(progress1);
            m3cYellowX = m6cYellowX = c3yellowX - stage1Distance(progress1);
            m4cYellowX = m5cYellowX = c4yellowX - stage1Distance(progress1);
            m3YellowX = p3yellowX - +stage1Distance(progress1);
        } else {
            float progress2 = 1 + (progress - 1f) / 0.7f;
            m3YellowX = p3yellowX - stage5Distance(progress2);

            m3cYellowX = m6cYellowX = c3yellowX - stage4Distance(progress2);
            m4cYellowX = m5cYellowX = c4yellowX - stage4Distance(progress2);

            m2YellowX = m4YellowX = p2yellowX - stage2Distance(progress2);
            m1cYellowX = m8cYellowX = c1yellowX - stage2Distance(progress2);
            m2cYellowX = m7cYellowX = c2yellowX - stage2Distance(progress2);
            m1YellowX = p1yellowX - stage2Distance(progress2);
        }
    }

    private void red2Left() {
        if (progress <= 0.3f) {
            float progress1 = 1.0f + ((progress - 0.3f) / 0.3f);
            m1RedX = p1RedX - stage1Distance(progress1);
            m1cRedX = m8cRedX = c1RedX - stage1Distance(progress1);
            m2cRedX = m7cRedX = c2RedX - stage1Distance(progress1);
            m2RedX = m4RedX = p2RedX - stage1Distance(progress1);
            m3cRedX = m6cRedX = c3RedX - stage1Distance(progress1);
            m4cRedX = m5cRedX = c4RedX - stage1Distance(progress1);
            m3RedX = p3RedX - stage1Distance(progress1);
        } else {
            float progress2 = 1 + (progress - 1f) / 0.7f;
            m3RedX = p3RedX - stage5Distance(progress2);

            m3cRedX = m6cRedX = c3RedX - stage4Distance(progress2);
            m4cRedX = m5cRedX = c4RedX - stage4Distance(progress2);

            m2RedX = m4RedX = p2RedX - stage2Distance(progress2);
            m1cRedX = m8cRedX = c1RedX - stage2Distance(progress2);
            m2cRedX = m7cRedX = c2RedX - stage2Distance(progress2);
            m1RedX = p1RedX - stage2Distance(progress2);
        }
    }


    private float stage1Distance(float progress) {
        return (distance / 4) * progress;
    }

    private float stage2Distance(float progress) {
        return ((distance * 3 / 4 + 2 * radius) * progress) + distance / 4;
    }

    private float stage4Distance(float progress) {
        return (float) ((distance * 3 / 4 + 2 * radius) * Math.sin(progress * Math.PI / 2)) + distance / 4;
    }

    private float stage5Distance(float progress) {
        return -(distance * 3 / 4 + 2 * radius) * progress * progress + 2 * (distance * 3 / 4 + 2 * radius) * progress + +distance / 4;
    }

    private float stage6Distance(float progress) {
        return (distance * 3 / 4 + 2 * radius) * progress * progress + distance / 4;
    }

    private float stage7Distance(float progress) {
        return (float) (-Math.pow((progress - 0.5f), 2) + 0.25f);
    }

    private float progress;
    ValueAnimator animator;

    public void setUpAnimator() {

        animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(animation -> {
            progress = (Float) animation.getAnimatedValue();
            if (toRight % 2 == 0) {
                yellow2Right();
                red2Left();
            } else {
                yellow2Left();
                red2Right();
            }
            invalidate();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                toRight++;
                progress = 0;
                p1yellowX = m1YellowX;
                p2yellowX = m2YellowX;
                p3yellowX = m3YellowX;
                c1yellowX = m1cYellowX;
                c2yellowX = m2cYellowX;
                c3yellowX = m3cYellowX;
                c4yellowX = m4cYellowX;

                p1RedX = m1RedX;
                p2RedX = m2RedX;
                p3RedX = m3RedX;
                c1RedX = m1cRedX;
                c2RedX = m2cRedX;
                c3RedX = m3cRedX;
                c4RedX = m4cRedX;
            }

        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(5000);
        animator.start();
    }

}
