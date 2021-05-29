package com.example.finalscannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAge;
    private Button register;
    private ImageView logo;
    private ProgressBar progressBar;

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

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

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

    }
}



























