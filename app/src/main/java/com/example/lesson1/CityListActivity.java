package com.example.lesson1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CityListActivity extends AppCompatActivity implements Constants {
    private final static int REQUEST_CODE = 2;

    String city;
    String weather;
    String tempCurrent;
    String tempDay;
    String tempNight;

    TextView cityContainer1;
    TextView cityContainer2;
    TextView cityContainer3;
    TextView cityContainer4;

    TextView weatherContainer1;
    TextView weatherContainer2;
    TextView weatherContainer3;
    TextView weatherContainer4;

    TextView tempCurrentContainer1;
    TextView tempCurrentContainer2;
    TextView tempCurrentContainer3;
    TextView tempCurrentContainer4;

    TextView tempDayContainer1;
    TextView tempDayContainer2;
    TextView tempDayContainer3;
    TextView tempDayContainer4;

    TextView tempNightContainer1;
    TextView tempNightContainer2;
    TextView tempNightContainer3;
    TextView tempNightContainer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);

        String instanceState;
        instanceState = (savedInstanceState == null) ? "Первый запуск!" : "Повторный запуск!";
        logCallbackMethods("onCreate() - " + instanceState);

        cityContainer1 = findViewById(R.id.city1_name);
        cityContainer2 = findViewById(R.id.city2_name);
        cityContainer3 = findViewById(R.id.city3_name);
        cityContainer4 = findViewById(R.id.city4_name);

        weatherContainer1 = findViewById(R.id.city1_weather);
        weatherContainer2 = findViewById(R.id.city2_weather);
        weatherContainer3 = findViewById(R.id.city3_weather);
        weatherContainer4 = findViewById(R.id.city4_weather);

        tempCurrentContainer1 = findViewById(R.id.city1_temp);
        tempCurrentContainer2 = findViewById(R.id.city2_temp);
        tempCurrentContainer3 = findViewById(R.id.city3_temp);
        tempCurrentContainer4 = findViewById(R.id.city4_temp);

        tempDayContainer1 = findViewById(R.id.city1_temp_day);
        tempDayContainer2 = findViewById(R.id.city2_temp_day);
        tempDayContainer3 = findViewById(R.id.city3_temp_day);
        tempDayContainer4 = findViewById(R.id.city4_temp_day);

        tempNightContainer1 = findViewById(R.id.city1_temp_night);
        tempNightContainer2 = findViewById(R.id.city2_temp_night);
        tempNightContainer3 = findViewById(R.id.city3_temp_night);
        tempNightContainer4 = findViewById(R.id.city4_temp_night);

        FrameLayout city1 = findViewById(R.id.city1);
        FrameLayout city2 = findViewById(R.id.city2);
        FrameLayout city3 = findViewById(R.id.city3);
        FrameLayout city4 = findViewById(R.id.city4);

        clickListener(city1, cityContainer1, weatherContainer1, tempCurrentContainer1, tempDayContainer1, tempNightContainer1);
        clickListener(city2, cityContainer2, weatherContainer2, tempCurrentContainer2, tempDayContainer2, tempNightContainer2);
        clickListener(city3, cityContainer3, weatherContainer3, tempCurrentContainer3, tempDayContainer3, tempNightContainer3);
        clickListener(city4, cityContainer4, weatherContainer4, tempCurrentContainer4, tempDayContainer4, tempNightContainer4);

        TextView startCityAddActivity = findViewById(R.id.add_city_start);
        startCityAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityListActivity.this, CityAddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 2) {
//            city.setText(data.getStringExtra(CITY));

            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);
            if (parcel != null) {
                cityContainer4.setText(String.valueOf(parcel.city));
                weatherContainer1.setText(String.valueOf(parcel.weather));
                tempCurrentContainer4.setText(String.valueOf(parcel.tempCurrent));
                tempDayContainer4.setText(String.valueOf(parcel.tempDay));
                tempNightContainer4.setText(String.valueOf(parcel.tempNight));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

        city        = saveInstanceState.getString(CITY);
        weather     = saveInstanceState.getString(WEATHER);
        tempCurrent = saveInstanceState.getString(TEMP_CURRENT);
        tempDay     = saveInstanceState.getString(TEMP_DAY);
        tempNight   = saveInstanceState.getString(TEMP_NIGHT);

        cityContainer1.setText(city);
        weatherContainer1.setText(weather);
        tempCurrentContainer4.setText(tempCurrent);
        tempDayContainer4.setText(tempDay);
        tempNightContainer4.setText(tempNight);
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

        city        = cityContainer1.getText().toString();
        weather     = weatherContainer1.getText().toString();
        tempCurrent = tempCurrentContainer4.getText().toString();
        tempDay     = tempDayContainer4.getText().toString();
        tempNight   = tempNightContainer4.getText().toString();

        saveInstanceState.putString(CITY, city);
        saveInstanceState.putString(WEATHER, weather);
        saveInstanceState.putString(TEMP_CURRENT, tempCurrent);
        saveInstanceState.putString(TEMP_DAY, tempDay);
        saveInstanceState.putString(TEMP_NIGHT, tempNight);
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

    private void clickListener(FrameLayout city, final TextView cityContainer, final TextView weatherContainer,
                               final TextView tempCurrentContainer, final TextView tempDayContainer, final TextView tempNightContainer) {
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Parcel parcel = new Parcel();
                parcel.city        = cityContainer.getText().toString();
                parcel.weather     = weatherContainer.getText().toString();
                parcel.tempCurrent = tempCurrentContainer.getText().toString();
                parcel.tempDay     = tempDayContainer.getText().toString();
                parcel.tempNight   = tempNightContainer.getText().toString();

                Intent intent = new Intent();
//                intent.putExtra(CITY, inputAddCity.getText().toString());
                intent.putExtra(CITY, parcel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}