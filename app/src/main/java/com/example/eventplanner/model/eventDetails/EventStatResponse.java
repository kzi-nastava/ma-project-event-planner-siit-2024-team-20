package com.example.eventplanner.model.eventDetails;

public class EventStatResponse {
    private int users; //total number of users
    private int liked; //those who liked
    private int one; //grade 1
    private int two; //grade 2
    private int three; //grade 3
    private int four; //grade 4
    private int five; //grade 5
    private int accepted; //if the event is private, the number of those who accepted invitations

    public EventStatResponse(int users, int liked, int one, int two, int three, int four, int five, int accepted) {
        this.users = users;
        this.liked = liked;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.accepted = accepted;
    }

    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getOne() {
        return one;
    }

    public void setOne(int one) {
        this.one = one;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public int getThree() {
        return three;
    }

    public void setThree(int three) {
        this.three = three;
    }

    public int getFour() {
        return four;
    }

    public void setFour(int four) {
        this.four = four;
    }

    public int getFive() {
        return five;
    }

    public void setFive(int five) {
        this.five = five;
    }

    public int getAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }
}
