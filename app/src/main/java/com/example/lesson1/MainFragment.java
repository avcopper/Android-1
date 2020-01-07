package com.example.lesson1;

import android.os.Bundle;
import android.view.View;
import java.io.Serializable;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        cityContainer        = getActivity().findViewById(R.id.cityCurrent);
        weatherContainer     = getActivity().findViewById(R.id.weather);
        tempCurrentContainer = getActivity().findViewById(R.id.temp_curr);
        tempDayContainer     = getActivity().findViewById(R.id.temp_day);
        tempNightContainer   = getActivity().findViewById(R.id.temp_night);
        humidityContainer    = getActivity().findViewById(R.id.humidity);
        windContainer        = getActivity().findViewById(R.id.wind);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null ) {
            String city           = (String)savedInstanceState.getSerializable(CITY);
            String weather        = (String) savedInstanceState.getSerializable(WEATHER);
            String tempCurrent    = (String) savedInstanceState.getSerializable(TEMP_CURRENT);
            String tempDay        = (String) savedInstanceState.getSerializable(TEMP_DAY);
            String tempNight      = (String) savedInstanceState.getSerializable(TEMP_NIGHT);
            String humiVisibility = (String)savedInstanceState.getSerializable(HUMIDITY_CONTAINER);
            String windVisibility = (String)savedInstanceState.getSerializable(WIND_CONTAINER);

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
        }

        if (isExistSecondView) {
            showSecond();
        }

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        Parcel parcel = getParcel();

        if (parcel != null) {
            // НЕ РАБОТАЕТ ЭТОТ МЕТОД ПОЛУЧЕНИЯ ЭЛЕМЕНТА ЧЕРЕЗ ИНФЛАТЕР
            // НЕ НАХОДИТ ЭЛЕМЕНТ И НЕ ЗАПИСЫВАЕТ ДАННЫЕ В НЕГО!!!
            //TextView cityCurrent = layout.findViewById(R.id.cityCurrent);
            cityContainer        = getActivity().findViewById(R.id.cityCurrent);
            weatherContainer     = getActivity().findViewById(R.id.weather);
            tempCurrentContainer = getActivity().findViewById(R.id.temp_curr);
            tempDayContainer     = getActivity().findViewById(R.id.temp_day);
            tempNightContainer   = getActivity().findViewById(R.id.temp_night);
            humidityContainer    = getActivity().findViewById(R.id.humidity);
            windContainer        = getActivity().findViewById(R.id.wind);

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

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView changeCity = view.findViewById(R.id.changeCity);
        changeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(new Parcel());
            }
        });

        ImageView settings = view.findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcel parcel = new Parcel();
                parcel.humidity = (humidityContainer.getVisibility() == View.VISIBLE ? 1 : -1);
                parcel.wind = (windContainer.getVisibility() == View.VISIBLE ? 1 : -1);

                showSettings(parcel);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 1) {
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);

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
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(CITY, (Serializable)cityContainer.getText());
        outState.putSerializable(WEATHER, (Serializable)weatherContainer.getText());
        outState.putSerializable(TEMP_CURRENT, (Serializable)tempCurrentContainer.getText());
        outState.putSerializable(TEMP_DAY, (Serializable)tempDayContainer.getText());
        outState.putSerializable(TEMP_NIGHT, (Serializable)tempNightContainer.getText());
        outState.putSerializable(HUMIDITY_CONTAINER, String.valueOf(((humidityContainer.getVisibility() == View.VISIBLE) ? 1 : -1)));
        outState.putSerializable(WIND_CONTAINER, String.valueOf(((windContainer.getVisibility() == View.VISIBLE) ? 1 : -1)));

        super.onSaveInstanceState(outState);
    }

    static MainFragment create(Parcel parcel) {
        MainFragment f = new MainFragment();    // создание

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    private void showSecond() {
        if (isExistSecondView) {
            Fragment second = getFragmentManager().findFragmentById(R.id.second);

            if (second == null) {
                second = CitiesFragment.create(new Parcel());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.second, second); // замена фрагмента
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
            Fragment second = getFragmentManager().findFragmentById(R.id.second);

            if (second == null || !(second instanceof CitiesFragment)) {
                second = CitiesFragment.create(new Parcel());

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.second, second); // замена фрагмента
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
            Fragment second = getFragmentManager().findFragmentById(R.id.second);

            if (second == null || !(second instanceof SettingsFragment)) {
                second = SettingsFragment.create(parcel);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.second, second); // замена фрагмента
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
}
