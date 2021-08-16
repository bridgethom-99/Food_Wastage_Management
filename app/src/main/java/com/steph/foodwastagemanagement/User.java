package com.steph.foodwastagemanagement;

public class User {
    public String fullName, Email,Position,PhoneNumber;



    public User(){

    }

    public User(String fullName, String email, String position, String phonenumber) {
        this.fullName = fullName;
        this.Email=email;
        this.Position=position;
        this.PhoneNumber=phonenumber;
    }
}
