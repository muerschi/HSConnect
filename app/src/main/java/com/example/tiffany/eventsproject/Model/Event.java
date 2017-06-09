package com.example.tiffany.eventsproject.Model;

import java.util.Date;

/**
 * Created by Tiffany on 09.06.2017.
 */


public class Event {

    private String title, location, description;
    private String faculty;
    private Date date, eventDate;

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

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public Date getDate () {
        this.date = new Date();
        return this.date;
    }
}

