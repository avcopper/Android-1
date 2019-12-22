package com.example.lesson1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Constants {
    private final static int REQUEST_CODE = 1;

    String city;
    String weather;
    String tempCurrent;
    String tempDay;
    String tempNight;

    TextView cityContainer;
    TextView weatherContainer;
    TextView tempCurrentContainer;
    TextView tempDayContainer;
    TextView tempNightContainer;

    LinearLayout humidityContainer;
    LinearLayout windContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String instanceState;
        instanceState = (savedInstanceState == null) ? "Первый запуск!" : "Повторный запуск!";
        logCallbackMethods("onCreate() - " + instanceState);

        int tempRandom = (new DataHandler()).generateNumber(-40, 40);

        tempCurrent = (tempRandom > 0 ? "+" : "") + tempRandom + "\u00B0";
        tempDay     = ((tempRandom + 3) > 0 ? "+" : "") + (tempRandom + 3) + "\u00B0";
        tempNight   = ((tempRandom - 3) > 0 ? "+" : "") + (tempRandom - 3) + "\u00B0";

        cityContainer        = findViewById(R.id.cityCurrent);
        weatherContainer     = findViewById(R.id.weather);
        humidityContainer    = findViewById(R.id.humidity);
        windContainer        = findViewById(R.id.wind);
        tempCurrentContainer = findViewById(R.id.temp_curr);
        tempDayContainer     = findViewById(R.id.temp_day);
        tempNightContainer   = findViewById(R.id.temp_night);

        tempCurrentContainer.setText(tempCurrent);
        tempDayContainer.setText(tempDay);
        tempNightContainer.setText(tempNight);

        ImageView changeCity = findViewById(R.id.changeCity);
        ImageView settings   = findViewById(R.id.settings);

        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcel parcel = new Parcel();
                parcel.humidity = (humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1);
                parcel.wind = (windContainer.getVisibility() == View.VISIBLE ? 1 : -1);

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra(CITY, parcel);

                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        cityContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://ru.wikipedia.org/wiki/" + cityContainer.getText().toString();

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
//            city.setText(data.getStringExtra(CITY));
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);

            if (parcel != null) {
                if (parcel.city != null) cityContainer.setText(parcel.city);
                if (parcel.weather != null) weatherContainer.setText(parcel.weather);
                if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
                if (parcel.tempDay != null) tempDayContainer.setText(parcel.tempDay);
                if (parcel.tempNight != null) tempNightContainer.setText(parcel.tempNight);

                if (parcel.humidity == -1) {
                    humidityContainer.setVisibility(View.GONE);
                } else {
                    humidityContainer.setVisibility(View.VISIBLE);
                }

                if (parcel.wind == -1) {
                    windContainer.setVisibility(View.GONE);
                } else {
                    windContainer.setVisibility(View.VISIBLE);
                }
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
        int humidityVisibility = Integer.parseInt(saveInstanceState.getString(HUMIDITY_CONTAINER));
        int windVisibility     = Integer.parseInt(saveInstanceState.getString(WIND_CONTAINER));

        cityContainer.setText(city);
        weatherContainer.setText(weather);
        tempCurrentContainer.setText(tempCurrent);
        tempDayContainer.setText(tempDay);
        tempNightContainer.setText(tempNight);

        if (humidityVisibility == -1) {
            humidityContainer.setVisibility(View.GONE);
        } else {
            humidityContainer.setVisibility(View.VISIBLE);
        }

        if (windVisibility == -1) {
            windContainer.setVisibility(View.GONE);
        } else {
            windContainer.setVisibility(View.VISIBLE);
        }
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

        saveInstanceState.putString(CITY, cityContainer.getText().toString());
        saveInstanceState.putString(WEATHER, weatherContainer.getText().toString());
        saveInstanceState.putString(TEMP_CURRENT, tempCurrentContainer.getText().toString());
        saveInstanceState.putString(TEMP_DAY, tempDayContainer.getText().toString());
        saveInstanceState.putString(TEMP_NIGHT, tempNightContainer.getText().toString());
        saveInstanceState.putString(WIND_CONTAINER, String.valueOf(windContainer.getVisibility() == View.VISIBLE ? 1 : -1));
        saveInstanceState.putString(HUMIDITY_CONTAINER, String.valueOf(humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1));
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