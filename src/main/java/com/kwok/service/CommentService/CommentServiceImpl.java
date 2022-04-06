package com.kwok.service.CommentService;

import com.kwok.mapper.CommentMapper;
import com.kwok.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("CommentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBlogId(int blogId) {
        List<Comment> comments = commentMapper.listCommentByBlogId(blogId);
        for (Comment comment : comments){
            int parent_id = comment.getParent_id();
            comment.setParentComment(commentMapper.getParentComment(parent_id));
            int id = comment.getId();
            List<Comment> replyCommentList = commentMapper.searchReplyComments(id);
            if (replyCommentList.size() != 0){
                for (Comment reComment : replyCommentList){
                    reComment.setParentComment(commentMapper.getParentComment(reComment.getParent_id()));
                    reComment.setReplyComments(commentMapper.searchReplyComments(reComment.getId()));
                }
            }
            comment.setReplyComments(replyCommentList);
        }
        return eachComment(comments);
    }

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        comment.setCreat_time(new Date());
        Comment lastComment = commentMapper.getLastComment();
        int i;
        if (lastComment != null){
            comment.setId(lastComment.getId()+1);
            i = commentMapper.saveComment(comment);
        }else {
            comment.setId(1);
            i = commentMapper.saveComment(comment);
        }
        return i;
    }

    /**
     * 循环每个顶级的评论节点
     * @params comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代表到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @params comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments){
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1){
                // 循环迭代，找出子代，存放在temReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱
     * @params comments 被迭代的对象
     * @return
     */
    private void recursively(Comment comment){
        tempReplys.add(comment); // 顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
