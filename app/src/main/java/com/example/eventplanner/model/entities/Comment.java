package com.example.eventplanner.model.entities;

import com.example.eventplanner.model.enumeration.StatusType;

public class Comment {
    private Long id;

    private String content;

    private StatusType status;

    private String user;

    private String item;

    public Comment(Long id, String content, String user,String item) {
        super();
        this.id = id;
        this.status=StatusType.PENDING;
        this.content = content;
        this.user = user;
        this.item=item;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
