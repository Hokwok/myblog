package com.kwok.mapper;

import com.kwok.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface CommentMapper {

    List<Comment> listCommentByBlogId(int blogId);

    List<Comment> searchReplyComments(int id);

    Comment getParentComment(int id);

    Comment getLastComment();

    int saveComment(Comment comment);

}
