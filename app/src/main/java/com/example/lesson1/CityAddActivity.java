package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CityAddActivity extends AppCompatActivity implements Constants {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_add);

        String instanceState;
        instanceState = (savedInstanceState == null) ? "Первый запуск!" : "Повторный запуск!";
        logCallbackMethods("onCreate() - " + instanceState);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonCityAdd = findViewById(R.id.button_add_city);
        buttonCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputAddCity = findViewById(R.id.input_add_city);
                String newCity = inputAddCity.getText().toString();

                if (!newCity.isEmpty()) {
                    int tempRandom = (new DataHandler()).generateNumber(-40, 40);
                    int weather    = (new DataHandler()).generateNumber(0, 4);

                    Parcel parcel = new Parcel();
                    parcel.city = inputAddCity.getText().toString();
                    parcel.weather = weathterList[weather];
                    parcel.tempCurrent = (tempRandom > 0 ? "+" : "") + tempRandom + "\u00B0";
                    parcel.tempDay = ((tempRandom + 3) > 0 ? "+" : "") + (tempRandom + 3) + "\u00B0";
                    parcel.tempNight = ((tempRandom - 3) > 0 ? "+" : "") + (tempRandom - 3) + "\u00B0";

                    Intent intent = new Intent();
//                    intent.putExtra(CITY, inputAddCity.getText().toString());
                    intent.putExtra(CITY, parcel);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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