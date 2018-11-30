package com.example.fanxiafei.myapplication.expandview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.fanxiafei.myapplication.R;


@SuppressLint("AppCompatCustomView")
public class ThemeExpandTextView extends TextView {

    public static final int STATUS_INIT = 0;

    public static final int STATUS_CLOSED = 1;

    public static final int STATUS_EXPAND = 2;

    private static final int MAX_LINES = 3;

    private int mStatus = STATUS_INIT;

    private SpannableString mSpanExpand = null;

    private String mExpandText = " 全文";

    private String mOriginalText;

    private OnExpandListener mOnExpandListener;

    public ThemeExpandTextView(Context context) {
        super(context);
    }

    public ThemeExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mStatus == STATUS_EXPAND || TextUtils.isEmpty(mOriginalText)) {
            return;
        }

        if (mSpanExpand == null) {
            Layout layout = createLayout(mOriginalText, w);
            if (layout.getLineCount() > MAX_LINES) {
                initSpanClosed();
                showClosedText(getWidth());
                mOnExpandListener.onExpand(STATUS_CLOSED);
            } else {
                mOnExpandListener.onExpand(STATUS_EXPAND);
            }
        }
    }

    public void setOnExpandListener(@NonNull OnExpandListener listener) {
        mOnExpandListener = listener;
    }

    public void setCommentText(@NonNull CharSequence text, int expand) {

        if (TextUtils.isEmpty(text)) {
            return;
        }
        setText(mOriginalText = text.toString());
        this.mStatus = expand;
        post(this::requestLayout);
    }

    public void reset() {
        mSpanExpand = null;
        mStatus = STATUS_INIT;
        setMaxLines(Integer.MAX_VALUE);
    }

    private void initSpanClosed() {
        String content = mExpandText;
        mSpanExpand = new SpannableString(content);
        ButtonSpan span = new ButtonSpan(getContext(), v -> {
            ThemeExpandTextView.super.setMaxLines(Integer.MAX_VALUE);
            ThemeExpandTextView.super.setText(mOriginalText);
            if (mOnExpandListener != null) {
                mOnExpandListener.onExpand(STATUS_EXPAND);
            }
        }, R.color.colorAccent);
        mSpanExpand.setSpan(span, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private void showClosedText(int width) {
        setMaxLines(MAX_LINES);
        Layout layout = createLayout(mOriginalText, width);
        String closedText = mOriginalText.substring(0, layout.getLineEnd(MAX_LINES - 1) + 1);

        Layout currLayout = createLayout(closedText + "..." + mExpandText, width);
        while (currLayout.getLineCount() > MAX_LINES) {
            int lastSpace = closedText.length() - 1;
            if (lastSpace == -1) {
                break;
            }
            closedText = closedText.substring(0, lastSpace);
            currLayout = createLayout(closedText + "..." + mExpandText, width);
        }
        String showText = closedText + "...";
        setText(showText);
        append(mSpanExpand);
        setMovementMethod(LinkMovementMethod.getInstance());
        post(this::requestLayout);
    }

    private StaticLayout createLayout(CharSequence text, int width) {
        return new StaticLayout(text, getPaint(), width - getPaddingLeft() - getPaddingRight(), Layout.Alignment.ALIGN_NORMAL, getLineSpacingMultiplier(), getLineSpacingExtra(), false);
    }

    public class ButtonSpan extends ClickableSpan {

        View.OnClickListener onClickListener;
        private Context context;
        private int colorId;

        public ButtonSpan(Context context, View.OnClickListener onClickListener) {
            this(context, onClickListener, R.color.colorAccent);
        }

        public ButtonSpan(Context context, View.OnClickListener onClickListener, int colorId) {
            this.onClickListener = onClickListener;
            this.context = context;
            this.colorId = colorId;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(context.getResources().getColor(colorId));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
            if (onClickListener != null) {
                onClickListener.onClick(widget);
            }
        }

    }

    public interface OnExpandListener {
        void onExpand(int expand);
    }
}
