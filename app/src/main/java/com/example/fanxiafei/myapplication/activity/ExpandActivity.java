package com.example.fanxiafei.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.fanxiafei.myapplication.expandview.ThemeExpandTextView;

public class ExpandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeExpandTextView expandTextView = new ThemeExpandTextView(this);
        addContentView(expandTextView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        expandTextView.setOnExpandListener(expand -> {});
        expandTextView.setCommentText("茫茫的长白大山，浩瀚的原始森林，大山脚下，原始森林环抱中散落着几十户人家的一个小山村，茅草房，对面炕，烟筒立在屋后边。在村东头有一个独立的房子，那就是青年点 窗前有一道小溪流过。学子在这里吃饭，由这里出发每天随社员去地里干活。干的活要么上山伐 树，抬树，要么砍柳树毛子开荒种地。在山里，可听那吆呵声：“顺山倒了！”放树谨防回头棒！ 树上的枯枝打到别的树上再蹦回来，这回头棒打人最厉害。", 0);
    }
}
