package com.example.carecodeee;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MyMedicinesActivity extends AppCompatActivity {

    EditText etMedicineName;
    TimePicker timePicker;
    Button btnAddMedicine, btnViewMedicines;

    SharedPreferences sharedPreferences;
    static final String PREF_NAME = "MedicinesData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medicines);

        etMedicineName = findViewById(R.id.etMedicineName);
        timePicker = findViewById(R.id.timePicker);
        btnAddMedicine = findViewById(R.id.btnAddMedicine);
        btnViewMedicines = findViewById(R.id.btnViewMedicines);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btnAddMedicine.setOnClickListener(v -> {
            String medName = etMedicineName.getText().toString().trim();
            if (medName.isEmpty()) {
                Toast.makeText(this, "Enter medicine name", Toast.LENGTH_SHORT).show();
                return;
            }

            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
            String medicineEntry = medName + " at " + timeFormatted;

            // Save to SharedPreferences
            Set<String> medicineSet = new HashSet<>(sharedPreferences.getStringSet("medicineList", new HashSet<>()));
            medicineSet.add(medicineEntry);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("medicineList", medicineSet);
            editor.apply();

            scheduleNotification(medName, hour, minute);
            Toast.makeText(this, "Reminder set for " + medName, Toast.LENGTH_SHORT).show();
            etMedicineName.setText("");
        });

        btnViewMedicines.setOnClickListener(v -> {
            Intent intent = new Intent(MyMedicinesActivity.this, ViewMedicinesActivity.class);
            startActivity(intent);
        });
    }

    private void scheduleNotification(String medicineName, int hour, int minute) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("medName", medicineName);

        int requestCode = new Random().nextInt(10000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // If time is in the past, schedule for next day
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );
    }
}
