package com.example.cse441_project.data.showscreen;

import java.sql.Time;
import java.util.Date;

public class ShowScreen {
    private int availableSeat;
    private Time endTime;
    private String id;
    private String name;
    private Time startTime;
    private int unavailableSeat;
    private Date date;
    private int idMovie;

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public int getUnavailableSeat() {
        return unavailableSeat;
    }

    public void setUnavailableSeat(int unavailableSeat) {
        this.unavailableSeat = unavailableSeat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ShowScreen() {
    }

    public ShowScreen(int availableSeat, Time endTime, String id, String name, Time startTime, int unavailableSeat, Date date) {
        this.availableSeat = availableSeat;
        this.endTime = endTime;
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.unavailableSeat = unavailableSeat;
        this.date = date;
    }
}
