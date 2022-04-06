package com.kwok.controller;

import com.kwok.pojo.Comment;
import com.kwok.pojo.User;
import com.kwok.service.BlogService.BlogService;
import com.kwok.service.CommentService.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") int blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        int blogId = comment.getBlog_id();
        User user = (User) session.getAttribute("user");
        if (user != null && comment.getNickname().equals(user.getNickname())){
            comment.setAvatar(user.getAvatar());
            comment.setIsadmin(1);
        }else if (comment.getGender().equals("1")){
            comment.setAvatar("/images/boy.png");
            comment.setIsadmin(0);
        }else if (comment.getGender().equals("0")){
            comment.setAvatar("/images/girl.png");
            comment.setIsadmin(0);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }

}
