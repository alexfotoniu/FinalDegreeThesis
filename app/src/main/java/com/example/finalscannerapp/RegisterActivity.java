package com.example.finalscannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Register Activity";
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAge;
    private Button register;
    private Button test;
    private ImageView logo;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootNode;
    private DatabaseReference mDatabase = FirebaseDatabase
            .getInstance()
            .getReference();


//    mDatabase = FirebaseDatabase.getInstance().getReference();

//    private DatabaseReference mDatabasee;



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

        test = (Button)findViewById(R.id.test_button);



        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();




//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.push().setValue("Hello");
//            }
//        });

//        mDatabasee = FirebaseDatabase.getInstance().getReference();
//        mDatabasee.child("messages").setValue("Hello, World");

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

                        FirebaseUser mDbData = mAuth.getCurrentUser();
                        DatabaseReference mUser = mDatabase.child("users");

                                mUser.child(Objects.requireNonNull(mDbData).getUid())
                                .setValue("aaa")
//                                        .addOnCompleteListener(task1 -> {
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(RegisterActivity.this, "User has been registered", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        System.out.println("Data inserted");
                                        Log.d(TAG,"Data inserted");

                                }}).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull @NotNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Registering failed", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        System.out.println("Fail");
                                        Log.d(TAG,"Failed to insert data");
                                    }
                                });




//                        });

//                        mDatabase.child("users").push().child(mAuth.getCurrentUser().getUid()).setValue(user);
//                        progressBar.setVisibility(View.GONE);
                        System.out.println("Success");
                        Log.d(TAG,"Data inserted successfully");

                    } else {
                        Toast.makeText(RegisterActivity.this, "Registering failed", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        System.out.println("Fail 2");
                        Log.d(TAG,"Data inserted failed");
                    }
                });




    }
}



























