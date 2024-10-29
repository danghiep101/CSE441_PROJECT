package com.example.admincse441project.data.model.ticket;

public class Ticket {
    private String id;
    private String movie_name;
    private String screen;
    private String seat;
    private String date;
    private String time;
    private String status;

    public Ticket() {}

    public Ticket(String id, String movie_name, String screen, String seat, String date, String time, String status) {
        this.id = id;
        this.movie_name = movie_name;
        this.screen = screen;
        this.seat = seat;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}