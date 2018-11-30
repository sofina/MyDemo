package com.example.fanxiafei.myapplication.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CommonRecycleViewDivider extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = 511;
    public static final int VERTICAL = 512;
    public static final int GRID = 513;

    private Paint mPaint;
    private int mDividerHeight;
    private int mOrientation;
    private float padding;

    /**
     * 自定义分割线
     *
     * @param orientation   分割线类型： HORIZONTAL VERTICAL GRID
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public CommonRecycleViewDivider(int orientation, float padding, int dividerHeight, int dividerColor) {
        if (orientation != VERTICAL && orientation != HORIZONTAL && orientation != GRID) {
            throw new IllegalArgumentException("orientation unavailable!");
        }

        this.mOrientation = orientation;
        this.padding = padding;
        this.mDividerHeight = dividerHeight;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * 绘制分割线
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        switch (mOrientation) {
            case HORIZONTAL:
                drawHorizontalDivider(c, parent);
                break;
            case VERTICAL:
                drawVerticalDivider(c, parent);
                break;
            case GRID:
                drawGridDivider(c, parent);
                break;
            default:
                break;
        }
    }

    /**
     * 绘制水平分割线
     */
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = (int) (child.getLeft() + padding);
            final int right = (int) (child.getRight() - padding);
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;

            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制垂直分割线
     */
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = (int) (child.getTop() + padding);
            final int bottom = (int) (child.getBottom() - padding);
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerHeight;

            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 绘制宫格分割线
     */
    private void drawGridDivider(Canvas canvas, RecyclerView parent) {
        if (parent == null || !(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }

        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        final int spanCount = layoutManager.getSpanCount();
        final int childSize = parent.getChildCount();

        // 最后一行（列）不画分割线
        for (int i = 0; i < getLastLine(childSize, spanCount); i++) {
            final View child = parent.getChildAt(i);
            if (layoutManager.getOrientation() == GridLayoutManager.HORIZONTAL) {
                drawGridVertical(child, canvas);
            } else {
                drawGridHorizon(child, canvas);
            }
        }

        //每行（列）的最后一个item不画分割线
        for (int i = 0; i < childSize - 1; i++) {
            if (spanCount > 0 && (i + 1) % spanCount == 0) {
                continue;
            }
            final View child = parent.getChildAt(i);
            if (layoutManager.getOrientation() == GridLayoutManager.HORIZONTAL) {
                drawGridHorizon(child, canvas);
            } else {
                drawGridVertical(child, canvas);
            }

        }
    }

    /**
     * 获取最后一列（行）
     */
    private int getLastLine(int childSize, int spanCount) {
        int mod = childSize % spanCount;
        int last;
        if (mod == 0) {
            last = childSize - spanCount;
        } else {
            last = childSize - mod;
        }
        return last;
    }

    private void drawGridVertical(final View child, Canvas canvas) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int top = child.getTop();
        final int bottom = child.getBottom();
        final int left = child.getRight() + layoutParams.rightMargin;
        final int right = left + mDividerHeight;

        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawGridHorizon(final View child, Canvas canvas) {
        final int top = child.getBottom();
        final int bottom = (top + mDividerHeight);
        final int left = child.getLeft();
        final int right = child.getRight();
        if (mPaint != null) {
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
