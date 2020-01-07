package com.example.lesson1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class CityListActivity extends AppCompatActivity implements Constants {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
    }
}