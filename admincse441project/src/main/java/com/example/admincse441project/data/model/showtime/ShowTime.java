package com.example.admincse441project.data.model.showtime;

public class ShowTime {
    private String id;
    private String name;
    private String availableSeat;
    private String unavailableSeat;
    private String startTime; // Thay đổi thành String
    private String endTime;   // Thay đổi thành String
    private String date;      // Thêm trường date
    private String idMovie;   // Thêm trường idMovie
    private String nameCinema;

    // Constructor rỗng cần thiết cho Firebase
    public ShowTime() {
    }

    public ShowTime(String id, String name, String availableSeat, String unavailableSeat, String startTime, String endTime, String date, String idMovie,String nameCinema) {
        this.id = id;
        this.name = name;
        this.availableSeat = availableSeat;
        this.unavailableSeat = unavailableSeat;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date; // Khởi tạo trường date
        this.idMovie = idMovie; // Khởi tạo trường idMovie
        this.nameCinema=nameCinema;
    }

    // Getter và Setter cho mỗi thuộc tính
    public String getId() {
        return id;
    }
    public String getNameCinema() {
        return nameCinema;
    }
    public void setNameCinema(String nameCinema) {
        this.nameCinema = nameCinema;
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

    public String getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(String availableSeat) {
        this.availableSeat = availableSeat;
    }

    public String getUnavailableSeat() {
        return unavailableSeat;
    }

    public void setUnavailableSeat(String unavailableSeat) {
        this.unavailableSeat = unavailableSeat;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date; // Getter cho trường date
    }

    public void setDate(String date) {
        this.date = date; // Setter cho trường date
    }

    public String getIdMovie() {
        return idMovie; // Getter cho trường idMovie
    }

    public void setIdMovie(String idMovie) {
        this.idMovie = idMovie; // Setter cho trường idMovie
    }
}
