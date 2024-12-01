package com.example.cse441_project.data.model.ticket;

public class Ticket {
    private String id;
    private String showtimeId;
    private String seat;
    private String userId;
    private String status;

    public Ticket() {}

    public Ticket(String id, String showtimeId, String seat, String userId, String status) {
        this.id = id;
        this.showtimeId = showtimeId;
        this.seat = seat;
        this.userId = userId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}