package com.example.carecodeee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView tvName, tvGender, tvAge, tvMobile, tvDoctor, tvDisease;
    ImageView ivProfile;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvName = findViewById(R.id.tvName);
        tvGender = findViewById(R.id.tvGender);
        tvAge = findViewById(R.id.tvAge);
        tvMobile = findViewById(R.id.tvMobile);
        tvDoctor = findViewById(R.id.tvDoctor);
        tvDisease = findViewById(R.id.tvDisease);
        ivProfile = findViewById(R.id.ivProfile);

        sharedPreferences = getSharedPreferences("CareCodeUserData", MODE_PRIVATE);

        // Load and display saved data
        tvName.setText(sharedPreferences.getString("name", "N/A"));
        tvGender.setText("Gender: " + sharedPreferences.getString("gender", "N/A"));
        tvAge.setText("Age: " + sharedPreferences.getString("age", "N/A"));
        tvMobile.setText("Mobile: " + sharedPreferences.getString("mobile", "N/A"));
        tvDoctor.setText("Doctor: " + sharedPreferences.getString("doctor", "N/A"));
        tvDisease.setText("Disease: " + sharedPreferences.getString("disease", "N/A"));


        Button btnMyMedicines = findViewById(R.id.btnMyMedicines);
        btnMyMedicines.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, MyMedicinesActivity.class);
            startActivity(intent);
        });

    }
}
