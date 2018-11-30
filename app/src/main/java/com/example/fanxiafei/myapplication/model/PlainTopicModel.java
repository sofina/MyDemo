package com.example.fanxiafei.myapplication.model;

public class PlainTopicModel extends TopicModelBase {

    private String topicId = "123456";    //话题id

    private int userId = 111111;  //用户id

    private String iconUrl = "http://inews.gtimg.com/newsapp_ls/0/5249128159_150120/0";    //头像url

    private String nickName = "西欧安排";   //用户昵称

    private String content = "啊啊啊啊啊啊";      //用户UGC内容

    private String contentImgUrl= "http://inews.gtimg.com/newsapp_ls/0/5249128159_150120/0";   //用户UGC图片

    private int originalUserId; //被回复的用户id

    private String originalContent;  //被回复的内容

    private BriefDetailModel detail = new BriefDetailModel();   //原始文章信息

    private int commentCount = 500;    //评论数

    private int favorCount= 200;    //点赞数

    private boolean isFavored;    //此用户是否点赞数

    private String commentId;  //评论id

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImgUrl() {
        return contentImgUrl;
    }

    public void setContentImgUrl(String contentImgUrl) {
        this.contentImgUrl = contentImgUrl;
    }

    public BriefDetailModel getDetail() {
        return detail;
    }

    public void setDetail(BriefDetailModel detail) {
        this.detail = detail;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(int favorCount) {
        this.favorCount = favorCount;
    }

    public boolean isFavored() {
        return isFavored;
    }

    public void setFavored(boolean favored) {
        isFavored = favored;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOriginalUserId() {
        return originalUserId;
    }

    public void setOriginalUserId(int originalUserId) {
        this.originalUserId = originalUserId;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

}
