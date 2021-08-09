package com.steph.foodwastagemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotpassword;
    EditText txt_email, txt_password;
    Button signIn;
    RadioButton mevent,mrestaurant,mchildren;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);

        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(this);


        mrestaurant=(RadioButton)findViewById(R.id.restaurant);
        mevent=(RadioButton)findViewById(R.id.event);
        mchildren=(RadioButton)findViewById(R.id.children);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.signIn:
                userLogin();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }


    }


    private void userLogin() {
        String position="";

        String email = txt_email.getText().toString().trim();
        String password = txt_password.getText().toString().trim();

        if (email.isEmpty()) {
            txt_email.setError("Email is required!");
            txt_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.setError("Please enter a valid email");
            txt_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            txt_password.setError("Password id required!");
         txt_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
          txt_password.setError("Minimum password length is 6 characters!");
            txt_password.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



               assert user != null;
                if (user.isEmailVerified()) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else {
                    user.sendEmailVerification();
                    Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                }



                //redirect to user profile
                if (mevent.isChecked()){
                    startActivity(new Intent(MainActivity.this, EventOrganiser.class));


                }else if(mrestaurant.isChecked()){
                    startActivity(new Intent(MainActivity.this, RestaurantManager.class));
                }else {
                    startActivity(new Intent(MainActivity.this, ChildrensHome.class));
                }


                // startActivity(new Intent(MainActivity.this, ProfileActivity.class));

            } else {

                Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
            }

        });


    }
}







