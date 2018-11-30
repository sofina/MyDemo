package com.example.fanxiafei.myapplication.model;

public class HotTopicModel extends TopicModelBase {

    private String topicId = "123666";

    private String title = "昆山事件";

    private int updateCount = 5;

    private String desc = "宝马车主被反杀";

    private String iconUrl= "http://inews.gtimg.com/newsapp_ls/0/5249128159_150120/0";

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
