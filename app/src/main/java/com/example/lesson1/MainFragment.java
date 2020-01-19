package com.example.lesson1;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Constants {
    private final static int REQUEST_CODE = 1;
    private boolean isExistSecondView;

    private TextView cityContainer;
    private TextView weatherContainer;
    private TextView tempCurrentContainer;
    private TextView tempDayContainer;
    private TextView tempNightContainer;
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

        cityContainer        = view.findViewById(R.id.fragment_main_city);
        weatherContainer     = view.findViewById(R.id.fragment_main_weather);
        tempCurrentContainer = view.findViewById(R.id.fragment_main_temp_curr);
        tempDayContainer     = view.findViewById(R.id.fragment_main_temp_day);
        tempNightContainer   = view.findViewById(R.id.fragment_main_temp_night);
        humidityContainer    = view.findViewById(R.id.fragment_main_humidity);
        windContainer        = view.findViewById(R.id.fragment_main_wind);

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        checkState(savedInstanceState);
//        checkParcel();

        ImageView changeCity = view.findViewById(R.id.changeCity);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(new Parcel());
            }
        });

//        ImageView settings = view.findViewById(R.id.settings);
//        settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Parcel parcel = new Parcel();
//                parcel.humidity = (humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1);
//                parcel.wind = (windContainer.getVisibility() == View.VISIBLE ? 1 : -1);
//
//                showSettings(parcel);
//            }
//        });

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
                if (parcel.city != null) cityContainer.setText(parcel.city);
                if (parcel.weather != null) weatherContainer.setText(parcel.weather);
                if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
                if (parcel.tempDay != null) tempDayContainer.setText(parcel.tempDay);
                if (parcel.tempNight != null) tempNightContainer.setText(parcel.tempNight);

                if (parcel.humidity == -1) {
                    humidityContainer.setVisibility(View.GONE);
                } else if (parcel.humidity == 1) {
                    humidityContainer.setVisibility(View.VISIBLE);
                }

                if (parcel.wind == -1) {
                    windContainer.setVisibility(View.GONE);
                } else if (parcel.wind == 1) {
                    windContainer.setVisibility(View.VISIBLE);
                }

                if (parcel.dark == 1) {
                    Objects.requireNonNull(getActivity()).recreate();
                } else if (parcel.light == 1) {
                    Objects.requireNonNull(getActivity()).recreate();
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY, cityContainer.getText().toString());
        outState.putString(WEATHER, weatherContainer.getText().toString());
        outState.putString(TEMP_CURRENT, tempCurrentContainer.getText().toString());
        outState.putString(TEMP_DAY, tempDayContainer.getText().toString());
        outState.putString(TEMP_NIGHT, tempNightContainer.getText().toString());
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
        if (savedInstanceState != null ) {
            String city           = savedInstanceState.getString(CITY);
            String weather        = savedInstanceState.getString(WEATHER);
            String tempCurrent    = savedInstanceState.getString(TEMP_CURRENT);
            String tempDay        = savedInstanceState.getString(TEMP_DAY);
            String tempNight      = savedInstanceState.getString(TEMP_NIGHT);
            String humiVisibility = savedInstanceState.getString(HUMIDITY_CONTAINER);
            String windVisibility = savedInstanceState.getString(WIND_CONTAINER);

            cityContainer.setText(city);
            weatherContainer.setText(weather);
            tempCurrentContainer.setText(tempCurrent);
            tempDayContainer.setText(tempDay);
            tempNightContainer.setText(tempNight);

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
        } else {
            Parcel parcel = getParcel();

            if (parcel != null) {
                if (parcel.city != null) cityContainer.setText(parcel.city);
                if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
                if (parcel.tempDay != null) tempDayContainer.setText(parcel.tempDay);
                if (parcel.tempNight != null) tempNightContainer.setText(parcel.tempNight);
                if (parcel.weather != null) weatherContainer.setText(parcel.weather);

                if (parcel.humidity == -1) {
                    humidityContainer.setVisibility(View.GONE);
                } else if (parcel.humidity == 1) {
                    humidityContainer.setVisibility(View.VISIBLE);
                }

                if (parcel.wind == -1) {
                    windContainer.setVisibility(View.GONE);
                } else if (parcel.wind == 1) {
                    windContainer.setVisibility(View.VISIBLE);
                }
            }
        }
    }

//    private void checkParcel() {
//        Parcel parcel = getParcel();
//
//        if (parcel != null) {
//            if (parcel.city != null) cityContainer.setText(parcel.city);
//            if (parcel.tempCurrent != null) tempCurrentContainer.setText(parcel.tempCurrent);
//            if (parcel.tempDay != null) tempDayContainer.setText(parcel.tempDay);
//            if (parcel.tempNight != null) tempNightContainer.setText(parcel.tempNight);
//            if (parcel.weather != null) weatherContainer.setText(parcel.weather);
//
//            if (parcel.humidity == -1) {
//                humidityContainer.setVisibility(View.GONE);
//            } else if (parcel.humidity == 1) {
//                humidityContainer.setVisibility(View.VISIBLE);
//            }
//
//            if (parcel.wind == -1) {
//                windContainer.setVisibility(View.GONE);
//            } else if (parcel.wind == 1) {
//                windContainer.setVisibility(View.VISIBLE);
//            }
//        }
//    }

    private int[] getImageArray(){
        TypedArray pictures = getResources().obtainTypedArray(R.array.weather_image_collection);
        int length = pictures.length();
        int[] answer = new int [length];
        for ( int i = 0 ; i < length; i++){
            answer[i] = pictures.getResourceId(i, 0);
        }
        return answer;
    }
}