package com.steph.foodwastagemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Objects;

public class EventOrganiser extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference databaseRef;
    private DatabaseReference mDatabaseUsers;
    private EditText date, location, event_category;
    private TextView surplus, greetingTextView;
    private int mYear;
    private int mMonth;
    private int mDay;
    RadioButton yes;
    Button logout;

    //storing data
    DatabaseReference reference;
    String userID;
    FirebaseAuth fAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organiser);
        logout = (Button) findViewById(R.id.signOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EventOrganiser.this, MainActivity.class));
            }
        });


        //Implementing date picker

        date = findViewById(R.id.date);
        date.setOnClickListener(this);
        location = findViewById(R.id.location);
        event_category = findViewById(R.id.event_category);
        Spinner event_spinner=findViewById(R.id.event_spinner);
        if (event_spinner!=null){
          event_spinner.setOnItemSelectedListener(this);
        }
        //ArrayAdapter<CharSequence>ad
        yes = (RadioButton) findViewById(R.id.yes);
//storing data in the database
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Events");
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        user = fAuth.getCurrentUser();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());


        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fAuth.getCurrentUser().getUid();

         final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
/*
        reference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);

                if (userProfile != null) {

                    String fullName = userProfile.fullName;


                    greetingTextView.setText(String.format("Welcome, %s!", fullName));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventOrganiser.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });

 */


    }


    //implementing calender picker
    @Override
    public void onClick(View v) {
        if (v == date) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date.setText(dayOfMonth + "-" + (month + 1 + "_" + year));

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void AddEvent(View view) {
        String Availability = "";
        final String EventType = event_category.getText().toString().trim();
        final String Location = location.getText().toString().trim();
        final String Date = date.toString().trim();
        if (EventType.isEmpty()) {
            event_category.setError("Event type is required");
            event_category.requestFocus();
            return;
        }
        if (Location.isEmpty()) {
            location.setError("Location is required");
            location.requestFocus();
        }
        if (Date.isEmpty()) {
            date.setError("Date is required");
            date.requestFocus();
            return;
        }
        if (yes.isChecked()) {
            Availability = "Yes";


        } else {
            Availability = "No";
        }
        String finalPosition = Availability;

        //final String user_id = fAuth.getCurrentUser().getUid();
        final DatabaseReference newEvent = databaseRef.push();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users userProfile = snapshot.getValue(Users.class);

                if (userProfile != null) {

                    String fullName = userProfile.fullName;


                    greetingTextView.setText(String.format("Welcome, %s!", fullName));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventOrganiser.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });


        mDatabaseUsers.addValueEventListener(new ValueEventListener() {

                                                 @Override
                                                 public void onDataChange(DataSnapshot snapshot) {
                                                     newEvent.child("Event name").setValue(EventType);
                                                     newEvent.child("Location").setValue(Location);
                                                     newEvent.child("Date").setValue(Date);
                                                     newEvent.child("uid").setValue(user.getUid());

                                                     newEvent.child("Availability").setValue(finalPosition);


                                                 }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}

















