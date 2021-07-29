package com.steph.foodwastagemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements OnItemSelectedListener{

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;


    private Button logout;
    private String mSpinnerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = (Button) findViewById(R.id.signOut);
       // Spinner userSpinner = (Spinner) findViewById(R.id.user_spinner);

        /*if (userSpinner !=null){
            userSpinner.setOnItemSelectedListener(this);
        }

*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.register_spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /*if (userSpinner !=null){
          userSpinner.setAdapter(adapter);
        }
        */

        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        Spinner userSpinner = (Spinner) findViewById(R.id.user_spinner);

        //final TextView userTypeTextView = (TextView) findViewById(R.id.userType);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User userProfile = snapshot.getValue(User.class);

               if (userProfile !=null){
                   String fullName = userProfile.fullName;
                   String email = userProfile.email;
                   //Where sth could be added

                   greetingTextView.setText(String.format("Welcome, %s!", fullName));
                   fullNameTextView.setText(fullName);
                   emailTextView.setText(email);
                   userSpinner.setAdapter(adapter);
                   //userTypeTextView.setText(user_type);
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSpinnerLabel = adapterView.getItemAtPosition(i).toString();

        Toast myToast = Toast.makeText(this,"Selected user is a: " +mSpinnerLabel, Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast toast = Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT);
        toast.show();

    }
}