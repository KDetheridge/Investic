package com.example.investic.data.model;

public class User {
    public Integer userID;
    public String emailAddress;
    public String username;
    public String password;

    public User(Integer userID,String email, String username){
        this.userID = userID;
        this.emailAddress = email;
        this.username=username;

    }


    public String toString(){
        return "Email Address: " + this.emailAddress + ", Username: " + this.username;
    }

    public String getUsername(){
        return this.username;
    }
    public String getEmailAddress(){
        return this.emailAddress;
    }

    public Integer getID(){
        return this.userID;
    }
}