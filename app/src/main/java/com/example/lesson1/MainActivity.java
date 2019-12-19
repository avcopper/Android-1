package com.example.lesson1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String temp_current;
    String temp_day;
    String temp_night;

    TextView tempCurrentContainer;
    TextView tempDayContainer;
    TextView tempNightContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.activity_cities);
//        setContentView(R.layout.activity_settings);
//        setContentView(R.layout.activity_cities_add);

        String instanceState;
        instanceState = (savedInstanceState == null) ? "Первый запуск!" : "Повторный запуск!";

        Toast.makeText(getApplicationContext(), "onCreate() - " + instanceState, Toast.LENGTH_SHORT).show();
        Log.d("1. ", "onCreate()");

        int temp_random = generateNumber(-40, 40);

        temp_current = (temp_random > 0 ? "+" : "") + temp_random + "\u00B0";
        temp_day = ((temp_random + 3) > 0 ? "+" : "") + (temp_random + 3) + "\u00B0";
        temp_night = ((temp_random - 3) > 0 ? "+" : "") + (temp_random - 3) + "\u00B0";

        tempCurrentContainer = findViewById(R.id.temp_curr);
        tempDayContainer = findViewById(R.id.temp_day);
        tempNightContainer = findViewById(R.id.temp_night);

        tempCurrentContainer.setText(temp_current);
        tempDayContainer.setText(temp_day);
        tempNightContainer.setText(temp_night);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT).show();
        Log.d("2. ", "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);
        Toast.makeText(getApplicationContext(), "onRestoreInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("3. ", "onRestoreInstanceState()");

        temp_current = saveInstanceState.getString("temp_current");
        temp_day = saveInstanceState.getString("temp_day");
        temp_night = saveInstanceState.getString("temp_night");

        tempCurrentContainer.setText(temp_current);
        tempDayContainer.setText(temp_day);
        tempNightContainer.setText(temp_night);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT).show();
        Log.d("4. ", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT).show();
        Log.d("5. ", "onPause()");
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        Toast.makeText(getApplicationContext(), "onSaveInstanceState()", Toast.LENGTH_SHORT).show();
        Log.d("6. ", "onSaveInstanceState()");

        saveInstanceState.putString("temp_current", temp_current);
        saveInstanceState.putString("temp_day", temp_day);
        saveInstanceState.putString("temp_night", temp_night);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT).show();
        Log.d("7. ", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart()", Toast.LENGTH_SHORT).show();
        Log.d("8. ", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d("9. ", "onDestroy()");
    }

    protected int generateNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        return (min + rnd.nextInt(max - min + 1));
    }
}