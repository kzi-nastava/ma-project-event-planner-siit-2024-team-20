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
    public CommentViewResponse(Long id,String content,String user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}

