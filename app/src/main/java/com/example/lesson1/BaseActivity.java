package com.example.lesson1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "THEME";
    private static final String IsDarkTheme = "IS_DARK_THEME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference , MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme , true);
    }

    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsDarkTheme , isDarkTheme);
        editor.apply();
    }
}
