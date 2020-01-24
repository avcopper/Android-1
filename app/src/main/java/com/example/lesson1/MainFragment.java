package com.example.lesson1;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.res.Configuration;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lesson1.model.WeatherRequest;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Constants {
//    private static final String WEATHER_API_KEY = "50827a7213ada81bde07134eec5501f3";
    private static final String WEATHER_API_KEY = "0240835eed185923190f675bf4e672cc";
    private static final String WEATHER_URL_CITY = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=ru&appid=" + WEATHER_API_KEY + "&q=";
    private static final String WEATHER_URL_CITY_HOURLY = "https://samples.openweathermap.org/data/2.5/forecast/hourly?London,us&appid=" + WEATHER_API_KEY + "&q=";

    private final static int REQUEST_CODE = 1;
    private boolean isExistSecondView;

    private String currentCity;
    private TextView cityContainer;
    private TextView weatherContainer;
    private TextView tempCurrentContainer;
    private TextView tempRangeContainer;
    private TextView humidityValue;
    private TextView windValue;
    private TextView windDirection;
    private TextView updated;
    private LinearLayout humidityContainer;
    private LinearLayout windContainer;

    public MainFragment() {
    }

    static MainFragment create(Parcel parcel) {
        MainFragment f = new MainFragment();

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isExistSecondView) {
            showSecond();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView changeCity = view.findViewById(R.id.changeCity);
        ImageView reload     = view.findViewById(R.id.reload);
        cityContainer        = view.findViewById(R.id.fragment_main_city);
        weatherContainer     = view.findViewById(R.id.fragment_main_weather);
        tempCurrentContainer = view.findViewById(R.id.fragment_main_temp_curr);
        tempRangeContainer   = view.findViewById(R.id.fragment_main_temp_range);
        humidityValue        = view.findViewById(R.id.main_humidity_value);
        windValue            = view.findViewById(R.id.main_speed_value);
        humidityContainer    = view.findViewById(R.id.fragment_main_humidity);
        windContainer        = view.findViewById(R.id.fragment_main_wind);
        windDirection        = view.findViewById(R.id.main_direction);
        updated              = view.findViewById(R.id.updated);

        Parcel parcel = getParcel();

        if (savedInstanceState != null ) {
            checkState(savedInstanceState);
        } else if (parcel != null) {
            checkParcel(parcel);
        } else {
            currentCity = cityContainer.getText().toString();
            reloadWeatherOnMain(currentCity);
        }

        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(new Parcel());
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCity = cityContainer.getText().toString();
                reloadWeatherOnMain(currentCity);
            }
        });

        Parcel parcelTime = new Parcel();
        parcelTime.time = getResources().getStringArray(R.array.time_collection);

        Parcel parcelDay = new Parcel();
        parcelDay.day = getResources().getStringArray(R.array.days_collection);
        parcelDay.temperature_collection = getResources().getIntArray(R.array.temperature_collection);
        parcelDay.weather_image_collection = parcelTime.weather_image_collection = getImageArray();

        RecyclerView recyclerViewTime = view.findViewById(R.id.recycler_view_time);
        recyclerViewTime.setHasFixedSize(true);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewTime.setAdapter(new RecyclerAdapterTime(parcelTime));

        RecyclerView recyclerViewDay = view.findViewById(R.id.recycler_view_day);
        recyclerViewDay.setHasFixedSize(true);
        recyclerViewDay.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewDay.setAdapter(new RecyclerAdapterDay(parcelDay));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 1) {
            Parcel parcel = (Parcel)Objects.requireNonNull(data.getExtras()).getSerializable(CITY);
            if (parcel != null) {
                if (parcel.city != null) reloadWeatherOnMain(parcel.city);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY, cityContainer.getText().toString());
        outState.putString(WEATHER, weatherContainer.getText().toString());
        outState.putString(TEMP_CURRENT, tempCurrentContainer.getText().toString());
        outState.putString(TEMP_RANGE, tempRangeContainer.getText().toString());
        outState.putString(WIND, windValue.getText().toString());
        outState.putString(WIND_DIRECTION, windDirection.getText().toString());
        outState.putString(HUMIDITY, humidityValue.getText().toString());
        outState.putString(UPDATED, updated.getText().toString());
        outState.putString(HUMIDITY_CONTAINER, String.valueOf(((humidityContainer.getVisibility() == View.VISIBLE) ? 1 : -1)));
        outState.putString(WIND_CONTAINER, String.valueOf(((windContainer.getVisibility() == View.VISIBLE) ? 1 : -1)));
        super.onSaveInstanceState(outState);
    }

    private void showSecond() {
        if (isExistSecondView) {
            Fragment second = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.activity_main_land_second);

            if (second == null) {
                second = CitiesFragment.create(new Parcel());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main_land_second, second); // замена фрагмента
                ft.setTransition(FragmentTransaction. TRANSIT_FRAGMENT_FADE );
                ft.commit();
            }
        } else {
            Intent intent = new Intent(getActivity(), CityListActivity.class);
//            intent.putExtra(PARCEL, parcel);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void showCities(Parcel parcel) {
        if (isExistSecondView) {
            Fragment second = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.activity_main_land_second);

            if (second == null || !(second instanceof CitiesFragment)) {
                second = CitiesFragment.create(new Parcel());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main_land_second, second); // замена фрагмента
                ft.setTransition(FragmentTransaction. TRANSIT_FRAGMENT_FADE );
                ft.commit();
            }
        } else {
            Intent intent = new Intent(getActivity(), CityListActivity.class);
//            intent.putExtra(PARCEL, parcel);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private void showSettings(Parcel parcel) {
        if (isExistSecondView) {
            Fragment second = Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.activity_main_land_second);

            if (second == null || !(second instanceof SettingsFragment)) {
                second = SettingsFragment.create(parcel);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.activity_main_land_second, second); // замена фрагмента
                ft.setTransition(FragmentTransaction. TRANSIT_FRAGMENT_FADE );
                ft.commit();
            }
        } else {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            intent.putExtra(PARCEL, parcel);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private Parcel getParcel() {
        return (Parcel)((getArguments() != null) ? getArguments().getSerializable(PARCEL) : null);
    }

    private void checkState(Bundle savedInstanceState) {
        String city           = savedInstanceState.getString(CITY);
        String weather        = savedInstanceState.getString(WEATHER);
        String tempCurrent    = savedInstanceState.getString(TEMP_CURRENT);
        String tempRange      = savedInstanceState.getString(TEMP_RANGE);
        String humidity       = savedInstanceState.getString(HUMIDITY);
        String wind           = savedInstanceState.getString(WIND);
        String windDeg        = savedInstanceState.getString(WIND_DIRECTION);
        String humiVisibility = savedInstanceState.getString(HUMIDITY_CONTAINER);
        String windVisibility = savedInstanceState.getString(WIND_CONTAINER);
        String updatedValue   = savedInstanceState.getString(UPDATED);

        cityContainer.setText(city);
        weatherContainer.setText(weather);
        tempCurrentContainer.setText(tempCurrent);
        tempRangeContainer.setText(tempRange);
        humidityValue.setText(humidity);
        windValue.setText(wind);
        windDirection.setText(windDeg);
        updated.setText(updatedValue);

        if (humiVisibility.equals("-1")) {
            humidityContainer.setVisibility(View.GONE);
        } else if (humiVisibility.equals("1")) {
            humidityContainer.setVisibility(View.VISIBLE);
        }

        if (windVisibility.equals("-1")) {
            windContainer.setVisibility(View.GONE);
        } else if (windVisibility.equals("1")) {
            windContainer.setVisibility(View.VISIBLE);
        }
    }

    private void checkParcel(Parcel parcel) {
        if (parcel.city != null) cityContainer.setText(parcel.city);
        if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
        if (parcel.tempRange != null) tempRangeContainer.setText(parcel.tempRange);
        if (parcel.weather != null) weatherContainer.setText(parcel.weather);

        if (parcel.humidityVisibility == -1) {
            humidityContainer.setVisibility(View.GONE);
        } else if (parcel.humidityVisibility == 1) {
            humidityContainer.setVisibility(View.VISIBLE);
        }

        if (parcel.windVisibility == -1) {
            windContainer.setVisibility(View.GONE);
        } else if (parcel.windVisibility == 1) {
            windContainer.setVisibility(View.VISIBLE);
        }
    }

    private int[] getImageArray(){
        TypedArray pictures = getResources().obtainTypedArray(R.array.weather_image_collection);
        int length = pictures.length();
        int[] answer = new int [length];
        for ( int i = 0 ; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }

    private void reloadWeatherOnMain(String currentCity)
    {
        try {
            final URL uri = new URL(WEATHER_URL_CITY + currentCity);
            final Handler handler = new Handler();
            Log.d("reload: ", "1. Start reload procedure");
            new Thread(new Runnable() {
                public void run() {
                    Log.d("reload: ", "2. Start thread");
                    HttpsURLConnection urlConnection = null;
                    try {
                        Log.d("reload: ", "3. Start connection");
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);

                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                cityContainer.setText(currentCity);
                                displayWeather(weatherRequest);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        Log.d("error: ", "City not found", e);
//                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.d("error: ", "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        Log.d("reload: ", "6. Close connection");

                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            Log.d("error: ", "Fail URI");
//            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        Log.d("reload: ", "4. BufferedReader");
        return in.lines().collect(Collectors.joining("\n"));
    }

    private void displayWeather(WeatherRequest weatherRequest){
        Log.d("reload: ", "5. setData");
        int temp = Math.round(weatherRequest.getMain().getTemp());
        String tempData = (temp > 0 ? "+" + temp : temp) + getResources().getString(R.string.deg);

        int tempMin = Math.round(weatherRequest.getMain().getTemp_min());
        String temperatureMin = (tempMin > 0 ? "+" + tempMin : tempMin) + getResources().getString(R.string.deg);
        int tempMax = Math.round(weatherRequest.getMain().getTemp_max());
        String temperatureMax = (tempMax > 0 ? "+" + tempMax : tempMax) + getResources().getString(R.string.deg);
        String tempRange = temperatureMin + " / " + temperatureMax;

        int humidity = Math.round(weatherRequest.getMain().getHumidity());
        String humidityData = humidity + getResources().getString(R.string.percent);
        String speedData = Math.round(weatherRequest.getWind().getSpeed()) + " " + getResources().getString(R.string.speed);

        int degrees = weatherRequest.getWind().getDeg();
        String direction = getWindDirection(degrees);

        String weather = getCloudy(weatherRequest.getClouds().getAll(), humidity, temp);

        tempCurrentContainer.setText(tempData);
        tempRangeContainer.setText(tempRange);
        humidityValue.setText(humidityData);
        windValue.setText(speedData);
        windDirection.setText(direction);
        weatherContainer.setText(weather);
        updated.setText(MessageFormat.format("{0}{1}{2}", getResources().getString(R.string.updated), " ", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime())));
    }

    private String getWindDirection(float degrees)
    {
        String wind = "";
        if (degrees > 337.5 && degrees <= 22.5) {
            wind = "С";
        } else if (degrees > 22.5 && degrees <= 67.5) {
            wind = "С-В";
        } else if (degrees > 67.5 && degrees <= 112.5) {
            wind = "В";
        } else if (degrees > 112.5 && degrees <= 157.5) {
            wind = "Ю-В";
        } else if (degrees > 157.5 && degrees <= 202.5) {
            wind = "Ю";
        } else if (degrees > 202.5 && degrees <= 247.5) {
            wind = "Ю-З";
        } else if (degrees > 247.5 && degrees <= 292.5) {
            wind = "З";
        } else if (degrees > 292.5 && degrees <= 337.5) {
            wind = "С-З";
        }
        return wind;
    }

    private String getCloudy(int clouds, int humidity, int temp)
    {
        String weather = "";
        if (humidity > 90) {
            if (temp > 0) {
                weather = getResources().getString(R.string.rain);
            } else {
                weather = getResources().getString(R.string.snow);
            }
        } else if (clouds >= 60) {
            weather = getResources().getString(R.string.cloudy);
        } else if (clouds < 60 && clouds >= 30) {
            weather = getResources().getString(R.string.party_cloudy);
        } else if (clouds < 30) {
            weather = getResources().getString(R.string.sunny);
        }
        return weather;
    }
}
