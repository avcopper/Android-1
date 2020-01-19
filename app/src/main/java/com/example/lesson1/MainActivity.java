package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends BaseActivity implements Constants {
    private final static int REQUEST_CODE = 1;
    private Toolbar toolbar;
    private LinearLayout humidityContainer;
    private LinearLayout windContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        humidityContainer = findViewById(R.id.fragment_main_humidity);
        windContainer = findViewById(R.id.fragment_main_wind);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomBar = findViewById(R.id.nav_view);
        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Parcel parcel = new Parcel();
                parcel.humidity = (humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1);
                parcel.wind = (windContainer.getVisibility() == View.VISIBLE ? 1 : -1);

                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra(PARCEL, parcel);
                startActivityForResult(intent, REQUEST_CODE);
                return true ;

            case R.id.action_authors:
                Snackbar.make(toolbar ,"Автор: @andrew", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Закрыть" , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this.getApplicationContext(),"Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true ;

            case R.id.action_exit:
                Snackbar.make(toolbar ,"Вы уверены?", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Выход" , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }).show();
                return true ;
        }

        return super .onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true ;

                case R.id.bottom_authors :
                    Snackbar.make(toolbar ,"Автор: @andrew", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Закрыть" , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this.getApplicationContext(),"Snackbar закрыт", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    return true ;

                case R.id.bottom_settings :
                    Parcel parcel = new Parcel();
                    parcel.humidity = (humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1);
                    parcel.wind = (windContainer.getVisibility() == View.VISIBLE ? 1 : -1);

                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    intent.putExtra(PARCEL, parcel);
                    startActivityForResult(intent, REQUEST_CODE);
                    return true ;
            }

            return false ;
        }
    };
}