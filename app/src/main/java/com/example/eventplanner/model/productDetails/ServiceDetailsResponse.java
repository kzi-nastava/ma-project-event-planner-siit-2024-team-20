package com.example.eventplanner.model.productDetails;

import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.entities.Grade;
import com.example.eventplanner.model.entities.Service;
import com.example.eventplanner.model.review.CommentViewResponse;
import com.example.eventplanner.model.review.GradeViewResponse;
import com.example.eventplanner.model.users.UserViewResponse;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class ServiceDetailsResponse {
    private Long id;

    private String name;

    private String description;

    private double price;

    private int discount;

    private Set<String> images=new HashSet<String>();

    private String specificity;

    private String category;

    private UserViewResponse provider;

    private double duration;
    private double minDuration;
    private double maxDuration;

    private double reservationDeadline;

    private double cancellationDeadline;

    private double reservationConfirmation;

    private LocalTime workingStart;

    private LocalTime workingEnd;

    private boolean isAvailable;

    private Set<CommentViewResponse> comments=new HashSet<CommentViewResponse>();
    private Set<GradeViewResponse> grades=new HashSet<GradeViewResponse>();

    public ServiceDetailsResponse(Service service) {
        super();
        this.id = service.getId();
        this.name = service.getName();
        this.description = service.getDescription();
        this.price = service.getPrice();
        this.discount = service.getDiscount();
        this.images = service.getImages();
        this.specificity = service.getSpecificity();
        this.category = service.getCategory().getName();
        this.duration = service.getDuration();
        this.minDuration = service.getMinDuration();
        this.maxDuration = service.getMaxDuration();
        this.reservationDeadline = service.getReservationDeadline();
        this.cancellationDeadline = service.getCancellationDeadline();
        this.reservationConfirmation = service.getReservationConfirmation();
        this.workingStart=service.getWorkingStart();
        this.workingEnd=service.getWorkingEnd();
        transformCommentDTO(service.getComments());
        transformGradeDTO(service.getGrades());
        this.isAvailable=service.isAvailable();
        this.provider=new UserViewResponse(service.getProvider());
    }
    private void transformCommentDTO(Set<Comment> comments) {
        for(Comment c:comments){
            this.comments.add(new CommentViewResponse(c));
        }
    }
    private void transformGradeDTO(Set<Grade> grades) {
        for(Grade g:grades){
            this.grades.add(new GradeViewResponse(g));
        }
    }
    public ServiceDetailsResponse(Long id, String name, String description, double price,
                                  int discount, Set<String> images, String specificity, String category,
                                  double duration, double minDuration, double maxDuration,double reservationDeadline,
                                  double cancellationDeadline, double reservationConfirmation, LocalTime workingStart, LocalTime workingEnd,
                                  Set<CommentViewResponse> comments, Set<GradeViewResponse> grades, boolean isAvailable, UserViewResponse provider) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.images = images;
        this.specificity = specificity;
        this.category = category;
        this.duration = duration;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.reservationConfirmation = reservationConfirmation;
        this.workingStart=workingStart;
        this.workingEnd=workingEnd;
        this.comments = comments;
        this.grades = grades;
        this.isAvailable=isAvailable;
        this.provider=provider;
    }
}
