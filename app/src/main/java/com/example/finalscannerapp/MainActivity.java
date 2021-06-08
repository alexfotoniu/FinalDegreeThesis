package com.example.finalscannerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private String TAG = "Main activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_FinalScannerApp);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.bringToFront();
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (checkSelfPermission())
                    startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miProfile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }



//    public void checkPermission() {
//        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            showCameraPreview();
//        }
//    }
}