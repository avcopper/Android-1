package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SettingsActivity extends BaseActivity implements Constants {
    private Toolbar toolbar;
    private RadioButton darkTheme;
    private RadioButton lightTheme;
    private CheckBox humidityContainer;
    private CheckBox windContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        humidityContainer = findViewById(R.id.settings_humidity);
        windContainer = findViewById(R.id.settings_wind);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Settings");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setIcon(R.drawable.sun);

        BottomNavigationView bottomBar = findViewById(R.id.nav_view);
        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        darkTheme = findViewById(R.id.settings_theme_dark);
        lightTheme = findViewById(R.id.settings_theme_light);

        if (isDarkTheme()) {
            darkTheme.setChecked(true);
            lightTheme.setChecked(false);
        } else {
            lightTheme.setChecked(true);
            darkTheme.setChecked(false);
        }

        darkTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDarkTheme(true);
                recreate();
            }
        });

        lightTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDarkTheme(false);
                recreate();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_exit:
                finish();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_authors:
                Snackbar.make(toolbar, "Автор: @andrew", Snackbar.LENGTH_LONG)
                        .setAction("Закрыть", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SettingsActivity.this.getApplicationContext(), "Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    finish();
                    return true;

                case R.id.bottom_authors:
                    Snackbar.make(toolbar ,"Автор: @andrew", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Закрыть" , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(SettingsActivity.this,"Snackbar закрыт", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    return true ;

                case R.id.bottom_settings:
                    return true ;
            }

            return false ;
        }
    };

    @Override
    public void onBackPressed(){
        Parcel parcel = new Parcel();
        parcel.dark = darkTheme.isChecked() ? 1 : -1;
        parcel.light = lightTheme.isChecked() ? 1 : -1;
        parcel.humidity = humidityContainer.isChecked() ? 1 : -1;
        parcel.wind = windContainer.isChecked() ? 1 : -1;
        showMain(parcel);
    }

    private void showMain(Parcel parcel) {
        Intent intent = new Intent();
        intent.putExtra(CITY, parcel);
        setResult(RESULT_OK, intent);
        finish();
    }
}