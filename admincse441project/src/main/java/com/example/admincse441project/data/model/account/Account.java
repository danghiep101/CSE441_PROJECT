package com.example.admincse441project.data.model.account;

import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class Account {
    private String uid;
    private String phoneNumber;
    private String email;
    private String username;
    private String dateOfBirth;
    private String address;
    private String gender;
    private boolean isAdmin;

    // Constructor, getter và setter
    public Account() {}


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public Account(String uid, String phoneNumber, String email, String username, String dateOfBirth, String address, String gender) {
        this.uid = uid;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
    }

    public String getRole() {
        return isAdmin ? "Admin" : "User";
    }
}

