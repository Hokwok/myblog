package com.kwok.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {

    private int id;
    private String nickname;
    private String gender;
    private String email;
    private String avatar;
    private String content;
    private Date creat_time;
    private int blog_id;
    private int parent_id;
    private int isadmin;

    private Comment parentComment;
    private List<Comment> replyComments = new ArrayList<>();

    public Comment() {
    }

    public Comment(int id, String nickname, String gender, String email, String avatar, String content, Date creat_time, int blog_id, int parent_id, int isadmin, Comment parentComment, List<Comment> replyComments) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.email = email;
        this.avatar = avatar;
        this.content = content;
        this.creat_time = creat_time;
        this.blog_id = blog_id;
        this.parent_id = parent_id;
        this.isadmin = isadmin;
        this.parentComment = parentComment;
        this.replyComments = replyComments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(Date creat_time) {
        this.creat_time = creat_time;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(int isadmin) {
        this.isadmin = isadmin;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", creat_time=" + creat_time +
                ", blog_id=" + blog_id +
                ", parent_id=" + parent_id +
                ", isadmin=" + isadmin +
                ", parentComment=" + parentComment +
                ", replyComments=" + replyComments +
                '}';
    }
}
