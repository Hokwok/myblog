package com.kwok.pojo;

import java.util.Date;
import java.util.List;

public class Blog {

    private int id;
    private String title; // 标题
    private String description; // 博客描述
    private String content; // 内容
    private String first_picture;    //首图地址
    private String flag; //原创标记
    private Integer views; //浏览次数
    private boolean appreciation; //赞赏是否开启
    private boolean share_statement; //版权是否开启
    private boolean commentabled; //评论是否开启
    private boolean published; //是否发布
    private boolean recommend; //是否推荐
    private Date create_time; //创建时间
    private Date update_time; //更新时间
    private int type_id;
    private int user_id;

    private String typeName;
    private List<Tag> tags;
    private User user;
    private Comment comment;

    public Blog() {
    }

    public Blog(int id, String title, String description, String content, String first_picture, String flag, Integer views, boolean appreciation, boolean share_statement, boolean commentabled, boolean published, boolean recommend, Date create_time, Date update_time, int type_id, int user_id, String typeName, List<Tag> tags, User user, Comment comment) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.first_picture = first_picture;
        this.flag = flag;
        this.views = views;
        this.appreciation = appreciation;
        this.share_statement = share_statement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.create_time = create_time;
        this.update_time = update_time;
        this.type_id = type_id;
        this.user_id = user_id;
        this.typeName = typeName;
        this.tags = tags;
        this.user = user;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirst_picture() {
        return first_picture;
    }

    public void setFirst_picture(String first_picture) {
        this.first_picture = first_picture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShare_statement() {
        return share_statement;
    }

    public void setShare_statement(boolean share_statement) {
        this.share_statement = share_statement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", first_picture='" + first_picture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", share_statement=" + share_statement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                ", type_id=" + type_id +
                ", user_id=" + user_id +
                ", typeName='" + typeName + '\'' +
                ", tags=" + tags +
                ", user=" + user +
                ", comment=" + comment +
                '}';
    }
}
