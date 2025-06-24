package com.example.eventplanner.model.eventDetails;

import com.example.eventplanner.model.entities.Activity;
import com.example.eventplanner.model.entities.Comment;
import com.example.eventplanner.model.entities.Event;
import com.example.eventplanner.model.entities.Grade;
import com.example.eventplanner.model.review.CommentViewResponse;
import com.example.eventplanner.model.review.GradeViewResponse;
import com.example.eventplanner.model.users.OrganizerResponse;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class EventDetailsResponse {
    private Long id;
    private String name;
    private String description;
    private String eventType;
    private Integer maxParticipants;
    private LocationResponse location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private OrganizerResponse organizer;
    private Set<ActivityResponse> activities=new HashSet<ActivityResponse>();
    private Set<CommentViewResponse> comments=new HashSet<CommentViewResponse>();
    private Set<GradeViewResponse> grades=new HashSet<GradeViewResponse>();

    public EventDetailsResponse(Long id, String name, String description, String eventType, Integer maxParticipants, LocationResponse location, LocalDateTime startDate, LocalDateTime endDate, OrganizerResponse organizer, Set<ActivityResponse> activities, Set<CommentViewResponse> comments, Set<GradeViewResponse> grades) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.eventType = eventType;
        this.maxParticipants = maxParticipants;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
        this.activities = activities;
        this.comments = comments;
        this.grades = grades;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(LocationResponse location) {
        this.location = location;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public OrganizerResponse getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerResponse organizer) {
        this.organizer = organizer;
    }

    public Set<ActivityResponse> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityResponse> activities) {
        this.activities = activities;
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
    public EventDetailsResponse(Event event) {
        this.id=event.getId();
        this.name=event.getName();
        this.description=event.getDescription();
        this.eventType=event.getEventType().getName().toString();
        this.maxParticipants=event.getMaxParticipants();
        if(event.getLocation()!=null)this.location=new LocationResponse(event.getLocation());
        this.startDate=event.getStartDate();
        this.endDate=event.getEndDate();
        if(event.getOrganizer()!=null) this.organizer=new OrganizerResponse(event.getOrganizer());
        transformActivityDTO(event.getActivities());
        transformCommentDTO(event.getComments());
        transformGradeDTO(event.getGrades());

    }
    public void transformActivityDTO(Set<Activity> activities) {
        for(Activity a:activities){
            this.activities.add(new ActivityResponse(a));
        }
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
}
