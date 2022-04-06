package com.kwok.service.CommentService;

import com.kwok.pojo.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public interface CommentService {
    List<Comment> listCommentByBlogId(int blogId);

    int saveComment(Comment comment);
}
