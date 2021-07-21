package com.steph.foodwastagemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.AndroidException;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView banner, register;
    private EditText editTextfullName, editTextemail, editTextpassword;
    private ProgressBar ProgressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        editTextfullName = (EditText) findViewById(R.id.fullName);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        ProgressBar = (ProgressBar) findViewById(R.id.ProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.register:
                register();
                break;
        }

    }

    private void register() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String fullName = editTextfullName.getText().toString().trim();
        if (fullName.isEmpty()) {
            editTextfullName.setError("Full name is required");
            editTextfullName.requestFocus();
            return;
        }
            if (email.isEmpty()) {
                editTextemail.setError("Email is required");
                editTextemail.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextemail.setError("please provide a valid email");
                editTextemail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextpassword.setError("password is required");
                editTextpassword.requestFocus();
                return;
            }
            if (password.length() < 6) {
                editTextpassword.setError("Min password length should be 6 characters!");
                editTextpassword.requestFocus();
                return;
            }
            ProgressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this, "user has been registered successfully!", Toast.LENGTH_LONG).show();
                                    ProgressBar.setVisibility(View.GONE);
                                } else {
                                    Toast.makeText(RegisterUser.this, "Failed to register!Try again", Toast.LENGTH_LONG).show();
                                    ProgressBar.setVisibility(View.GONE);
                                }

                            }
                        });
                    } else {
                        Toast.makeText(RegisterUser.this, "Failed to register!Try again", Toast.LENGTH_LONG).show();
                        ProgressBar.setVisibility(View.GONE);
                    }
                }
            });


        }
    }






