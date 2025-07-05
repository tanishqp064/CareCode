package com.example.carecodeee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etName, etGender, etAge, etMobile, etDoctor, etDisease;
    Button btnSave;

    SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "CareCodeUserData";
    public static final String LOGIN_PREF = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ Check login status
        SharedPreferences loginPrefs = getSharedPreferences(LOGIN_PREF, MODE_PRIVATE);
        if (!loginPrefs.getBoolean("loggedIn", false)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        etAge = findViewById(R.id.etAge);
        etMobile = findViewById(R.id.etMobile);
        etDoctor = findViewById(R.id.etDoctor);
        etDisease = findViewById(R.id.etDisease);
        btnSave = findViewById(R.id.btnSave);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String gender = etGender.getText().toString();
            String age = etAge.getText().toString();
            String mobile = etMobile.getText().toString();
            String doctor = etDoctor.getText().toString();
            String disease = etDisease.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", name);
            editor.putString("gender", gender);
            editor.putString("age", age);
            editor.putString("mobile", mobile);
            editor.putString("doctor", doctor);
            editor.putString("disease", disease);
            editor.apply();

            Toast.makeText(this, "User details saved!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        });
    }
}
