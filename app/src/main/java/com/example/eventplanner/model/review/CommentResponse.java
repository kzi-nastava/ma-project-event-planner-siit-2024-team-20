package com.example.eventplanner.model.review;

import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.enumeration.StatusType;
import com.example.eventplanner.model.users.UserResponse;

public class CommentResponse {
        private Long id;
        private String content;
        private StatusType status;
        private UserResponse user;
        private String item;

        public CommentResponse(Comment comment) {
            this.id=comment.getId();
            this.content=comment.getContent();
            this.status=comment.getStatus();
            if(comment.getUser()!=null)this.user=new UserResponse(comment.getUser());
            this.item=comment.getItem();
        }
        public CommentResponse(String content,UserResponse user) {
            this.content = content;
            this.user=user;
        }

        public CommentResponse(Long id,String content,StatusType status, UserResponse user,String item) {
            this.id = id;
            this.content = content;
            this.status=status;
            this.item=item;
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

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
