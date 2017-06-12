package com.example.tiffany.eventsproject.Model;

import java.util.Date;

/**
 * Created by Tiffany on 09.06.2017.
 */


public class Event {

    private String title, location, description;
    private String faculty, eventDate, time;
    private Date date;
    private int evID;

    public void setId(int id) {
        this.evID = id;
    }

    public int getId() {
        return this.evID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setLocation (String location) {
        this.location = location;
    }

    public String getLocation () {
        return this.location;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription () {
        return this.description;
    }

    public void setFaculty (String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return this.faculty;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public Date getDate () {
        this.date = new Date();
        return this.date;
    }

    public void setTime (String time ) { this.time = time; }

    public String getTime () { return this.time; }
}

