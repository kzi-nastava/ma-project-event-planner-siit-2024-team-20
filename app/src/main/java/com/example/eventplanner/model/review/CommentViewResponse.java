package com.example.eventplanner.model.review;

import com.example.eventplanner.model.entities.Comment;

public class CommentViewResponse {
    private Long id;
    private String content;
    private String user;

    public CommentViewResponse(Comment comment) {
        this.content=comment.getContent();
        if(comment.getUser()!=null)this.user=comment.getUser().getName()+" "+comment.getUser().getLastName();
    }

    // response
    public CommentViewResponse(Long id,String content,String user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }
}

