package com.example.admincse441project.data.model;

public class Admin {
    private String uid;
    private String email;
    private String username;
    private String phoneNumber;
    private String password;
    private String dateOfBirth;
    private String gender;
    private String address;

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Admin() {
    }

    public Admin(String uid, String email, String username, String phoneNumber, String password, String dateOfBirth, String address, String gender) {
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
    }
}
