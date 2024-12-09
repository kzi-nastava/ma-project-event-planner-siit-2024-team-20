package com.example.eventplanner.model;

public class Comment {
    private Long id;

    private String content;

    private StatusType status;

    private User user;

    private String item;

    public Comment(Long id, String content, User user,String item) {
        super();
        this.id = id;
        this.status=StatusType.PENDING;
        this.content = content;
        this.user = user;
        this.item=item;
    }
}
