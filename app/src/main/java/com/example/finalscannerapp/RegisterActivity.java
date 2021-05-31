package com.example.finalscannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAge;
    private Button register;
    private ImageView logo;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_FinalScannerApp);
        setContentView(R.layout.activity_register);

        logo = (ImageView)findViewById(R.id.app_logo);
        logo.setOnClickListener(this);

        editTextFullName = (EditText)findViewById(R.id.full_name_box);
        editTextAge = (EditText)findViewById(R.id.age);
        editTextEmail = (EditText)findViewById(R.id.email_box);
        editTextPassword = (EditText)findViewById(R.id.pass_box);

        register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();
        mRootNode = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_logo:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editTextFullName.setError("full name required".toUpperCase());
            editTextFullName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("age is required".toUpperCase());
            editTextAge.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("email is required".toUpperCase());
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("invalid email".toUpperCase());
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("password is required".toUpperCase());
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("password should be at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, age, email);
//                        System.out.println("User created");

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
//                                        System.out.println("Data inserted");
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Registering failed", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
//                                        System.out.println("Fail");
                                    }
                                });

//                        mDatabase.child("users").push().child(mAuth.getCurrentUser().getUid()).setValue(user);
//                        System.out.println("Success");

                    } else {
                        Toast.makeText(RegisterActivity.this, "Registering failed", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
//                        System.out.println("Fail 2");
                    }
                });




    }
}



























