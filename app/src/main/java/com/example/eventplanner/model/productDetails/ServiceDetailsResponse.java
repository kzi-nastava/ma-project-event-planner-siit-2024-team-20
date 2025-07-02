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

    private boolean available;

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
        this.available=service.isAvailable();
        this.provider=new UserViewResponse(service.getProvider());
    }

    public ServiceDetailsResponse() {

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
        this.available=isAvailable;
        this.provider=provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public String getSpecificity() {
        return specificity;
    }

    public void setSpecificity(String specificity) {
        this.specificity = specificity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UserViewResponse getProvider() {
        return provider;
    }

    public void setProvider(UserViewResponse provider) {
        this.provider = provider;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(double minDuration) {
        this.minDuration = minDuration;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public double getReservationDeadline() {
        return reservationDeadline;
    }

    public void setReservationDeadline(double reservationDeadline) {
        this.reservationDeadline = reservationDeadline;
    }

    public double getCancellationDeadline() {
        return cancellationDeadline;
    }

    public void setCancellationDeadline(double cancellationDeadline) {
        this.cancellationDeadline = cancellationDeadline;
    }

    public double getReservationConfirmation() {
        return reservationConfirmation;
    }

    public void setReservationConfirmation(double reservationConfirmation) {
        this.reservationConfirmation = reservationConfirmation;
    }

    public LocalTime getWorkingStart() {
        return workingStart;
    }

    public void setWorkingStart(LocalTime workingStart) {
        this.workingStart = workingStart;
    }

    public LocalTime getWorkingEnd() {
        return workingEnd;
    }

    public void setWorkingEnd(LocalTime workingEnd) {
        this.workingEnd = workingEnd;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Set<CommentViewResponse> getComments() {
        return comments;
    }

    public void setComments(Set<CommentViewResponse> comments) {
        this.comments = comments;
    }

    public Set<GradeViewResponse> getGrades() {
        return grades;
    }

    public void setGrades(Set<GradeViewResponse> grades) {
        this.grades = grades;
    }
}
