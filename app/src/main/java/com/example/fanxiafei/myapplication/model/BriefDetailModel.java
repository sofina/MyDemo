package com.example.fanxiafei.myapplication.model;

public class BriefDetailModel {

    private String originalDocId = "20180816A1C87E00";//原始文章docId

    private String originalShareUrl= "http://inews.gtimg.com/newsapp_ls/0/5249128159_150120/0"; //分享时的url

    private String originalStyle = "news";//原始文章的样式

    private String originalTitle = "大王叫我来巡山";//原始文章的title

    private String originalUrl= "http://inews.gtimg.com/newsapp_ls/0/5249128159_150120/0"; //原始文章图片，视频的url

    private int imgCount;  //图片数

    public String getOriginalStyle() {
        return originalStyle;
    }

    public void setOriginalStyle(String originalStyle) {
        this.originalStyle = originalStyle;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalDocId() {
        return originalDocId;
    }

    public void setOriginalDocId(String originalDocId) {
        this.originalDocId = originalDocId;
    }

    public String getOriginalShareUrl() {
        return originalShareUrl;
    }

    public void setOriginalShareUrl(String originalShareUrl) {
        this.originalShareUrl = originalShareUrl;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

}
