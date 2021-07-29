package com.steph.foodwastagemanagement;

import android.icu.text.Transliterator;

public class User {
    public String fullName, Email,Position,PhoneNumber;



    public User(){

    }

    public User(String fullName, String email, String position, String phonenumber) {
        this.fullName = fullName;
         Email=email;
        Position=position;
        PhoneNumber=phonenumber;
    }
}
