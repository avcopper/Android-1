package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class CityListActivity extends AppCompatActivity implements Constants {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomBar = findViewById(R.id.nav_view);
        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cities");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setIcon(R.drawable.sun);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.home:
            case R.id.action_exit:
                finish();
                return true ;

            case R.id.action_settings :
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true ;

            case R.id.action_authors :
                Snackbar. make( toolbar , "Автор: @andrew", Snackbar. LENGTH_LONG)
                        .setAction("Закрыть" , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(CityListActivity.this.getApplicationContext(),"Snackbar закрыт", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true ;

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

                case R.id.bottom_authors :
                    Snackbar.make(toolbar ,"Автор: @andrew", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Закрыть" , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(CityListActivity.this,"Snackbar закрыт", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                    return true;

                case R.id.bottom_settings:
                    startActivityForResult(new Intent(CityListActivity.this, SettingsActivity.class), 1);
                    return true;
            }

            return false ;
        }
    };
}