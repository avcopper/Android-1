package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements Constants {
    CheckBox humidityContainer;
    CheckBox windContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String instanceState;
        instanceState = (savedInstanceState == null) ? "Первый запуск!" : "Повторный запуск!";
        logCallbackMethods("onCreate() - " + instanceState);

        ImageView back    = findViewById(R.id.back);
        humidityContainer = findViewById(R.id.settings_humidity);
        windContainer     = findViewById(R.id.settings_wind);

        Parcel parcel = (Parcel)getIntent().getExtras().getSerializable(CITY);
        humidityContainer.setChecked(parcel.humidity == 1);
        windContainer.setChecked(parcel.wind == 1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humidityContainer = findViewById(R.id.settings_humidity);
                windContainer     = findViewById(R.id.settings_wind);

                Parcel parcel = new Parcel();
                parcel.humidity = humidityContainer.isChecked() ? 1 : -1;
                parcel.wind = windContainer.isChecked() ? 1 : -1;

                Intent intent = new Intent();
//                intent.putExtra(CITY, inputAddCity.getText().toString());
                intent.putExtra(CITY, parcel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        logCallbackMethods("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        logCallbackMethods("onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logCallbackMethods("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logCallbackMethods("onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        logCallbackMethods("onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logCallbackMethods("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logCallbackMethods("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logCallbackMethods("onDestroy()");
    }

    private void logCallbackMethods(String method) {
        Toast.makeText(getApplicationContext(), method, Toast.LENGTH_SHORT).show();
        Log.d("log: ", method);
    }
}