package com.example.fanxiafei.myapplication.model;

public abstract class TopicModelBase {

    public static final int TOPIC_STYLE_HOT = 218;

    public static final int TOPIC_STYLE_PLAIN = 219;

    private int style;

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
