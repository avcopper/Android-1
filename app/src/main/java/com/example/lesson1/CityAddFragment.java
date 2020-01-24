package com.example.lesson1;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.content.res.Configuration;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.lesson1.model.WeatherRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityAddFragment extends Fragment implements Constants {
    //    private static final String WEATHER_API_KEY = "50827a7213ada81bde07134eec5501f3";
    private static final String WEATHER_API_KEY = "0240835eed185923190f675bf4e672cc";
    private static final String WEATHER_URL_CITY = "https://api.openweathermap.org/data/2.5/weather?units=metric&lang=ru&appid=" + WEATHER_API_KEY + "&q=";

    private boolean isExistSecondView;

    private TextInputEditText inputAddCity;
    private String newCity;
    private Pattern checkCity = Pattern.compile("^[a-zа-яё]{2,}$");

    public CityAddFragment() {
    }

    static CityAddFragment create(Parcel parcel) {
        CityAddFragment f = new CityAddFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ;

        inputAddCity = view.findViewById(R.id.input_add_city);
        Button buttonCityAdd = view.findViewById(R.id.button_add_city);
        Button cancel = view.findViewById(R.id.button_cancel_city);

        buttonCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable city = inputAddCity.getText();
                if (city != null) {
                    newCity = firstUpperCase(city.toString());
                    Boolean check = validate(inputAddCity, checkCity, "Русские/английские буквы, не менее 2-х!");
                    if (check) {
                        getWeatherByCityName(newCity, inputAddCity);
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showCities(Parcel parcel) {
        if (isExistSecondView) {
            CitiesFragment cities = CitiesFragment.create(parcel);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_land_second, cities);  // замена фрагмента
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent();
//            intent.putExtra(CITY, inputAddCity.getText().toString());
            intent.putExtra(CITY, parcel);
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            }
        }
    }

    private Boolean validate(TextView tv, Pattern check, String message){
        String value = tv.getText().toString();
        if (check.matcher(value).matches()){
            hideError(tv);
            return true;
        } else {
            showError(tv, message);
            return false;
        }
    }

    private void showError(TextView view, String message) {
        view.setError(message);
    }

    private void hideError(TextView view) {
        view.setError(null);
    }

    private void getWeatherByCityName(String currentCity, TextInputEditText inputAddCity)
    {
        try {
            final URL uri = new URL(WEATHER_URL_CITY + currentCity);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        getLines(in);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Parcel parcel = new Parcel();
                                parcel.city = currentCity;
                                showCities(parcel);
                            }
                        });
                    } catch (FileNotFoundException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showError(inputAddCity, "Город не найден!");
                            }
                        });
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String getLines(BufferedReader in) {
        return in.lines().collect(Collectors.joining("\n"));
    }

    private String firstUpperCase(String word){
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
