package com.kitakits.app;

public class JobModel {
    private String title, poster, pay, location;

    public JobModel(String title, String poster, String pay, String location) {
        this.title = title;
        this.poster = poster;
        this.pay = pay;
        this.location = location;
    }

    public String getTitle() { return title; }
    public String getPoster() { return poster; }
    public String getPay() { return pay; }
    public String getLocation() { return location; }
}
