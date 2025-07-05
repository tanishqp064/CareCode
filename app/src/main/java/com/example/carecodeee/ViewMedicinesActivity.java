package com.example.carecodeee;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class ViewMedicinesActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> medicineList;
    SharedPreferences sharedPreferences;
    static final String PREF_NAME = "MedicinesData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medicines);

        listView = findViewById(R.id.listViewMedicines);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        Set<String> savedSet = sharedPreferences.getStringSet("medicineList", new HashSet<>());
        medicineList = new ArrayList<>(savedSet);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, medicineList);
        listView.setAdapter(adapter);
    }
}

