package com.example.fanxiafei.myapplication.model;

public class TopicModel<R, T> {

    private int returnCode;
    private int status;
    private R hotTopicCard;
    private T plainTopicCard;
    private transient String url;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public R getHotTopicCard() {
        return hotTopicCard;
    }

    public void setHotTopicCard(R hotTopicCard) {
        this.hotTopicCard = hotTopicCard;
    }

    public T getPlainTopicCard() {
        return plainTopicCard;
    }

    public void setPlainTopicCard(T plainTopicCard) {
        this.plainTopicCard = plainTopicCard;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
