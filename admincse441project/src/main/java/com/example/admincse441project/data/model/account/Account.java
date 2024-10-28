package com.example.admincse441project.data.model.account;

import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class Account {
    private String id;
    private String phoneNumber;
    private String email;
    private String username;
    private String dateOfBirth;
    private boolean isAdmin;

    // Constructor, getter và setter
    public Account() {}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public Account(String id, String phoneNumber, String email, String username, String dateOfBirth) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return isAdmin ? "Admin" : "User";
    }
}

