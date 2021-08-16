package com.steph.foodwastagemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EventOrganiser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organiser);
        startActivity(new Intent(EventOrganiser.this, ProfileActivity.class));
    }
}