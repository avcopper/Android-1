package com.example.lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment implements Constants {
    private final static int REQUEST_CODE = 2;
    boolean isExistSecondView;

    private FrameLayout city1, city2, city3, city4;
    TextView cityContainer1, cityContainer2, cityContainer3, cityContainer4;
    TextView weatherContainer1, weatherContainer2, weatherContainer3, weatherContainer4;
    TextView tempCurrentContainer1, tempCurrentContainer2, tempCurrentContainer3, tempCurrentContainer4;
    TextView tempDayContainer1, tempDayContainer2, tempDayContainer3, tempDayContainer4;
    TextView tempNightContainer1, tempNightContainer2, tempNightContainer3, tempNightContainer4;

    public CitiesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_cities, container, false);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        city1                 = layout.findViewById(R.id.city1);
        cityContainer1        = layout.findViewById(R.id.city1_name);
        weatherContainer1     = layout.findViewById(R.id.city1_weather);
        tempCurrentContainer1 = layout.findViewById(R.id.city1_temp);
        tempDayContainer1     = layout.findViewById(R.id.city1_temp_day);
        tempNightContainer1   = layout.findViewById(R.id.city1_temp_night);

        city2                 = layout.findViewById(R.id.city2);
        cityContainer2        = layout.findViewById(R.id.city2_name);
        weatherContainer2     = layout.findViewById(R.id.city2_weather);
        tempCurrentContainer2 = layout.findViewById(R.id.city2_temp);
        tempDayContainer2     = layout.findViewById(R.id.city2_temp_day);
        tempNightContainer2   = layout.findViewById(R.id.city2_temp_night);

        city3                 = layout.findViewById(R.id.city3);
        cityContainer3        = layout.findViewById(R.id.city3_name);
        weatherContainer3     = layout.findViewById(R.id.city3_weather);
        tempCurrentContainer3 = layout.findViewById(R.id.city3_temp);
        tempDayContainer3     = layout.findViewById(R.id.city3_temp_day);
        tempNightContainer3   = layout.findViewById(R.id.city3_temp_night);

        city4                 = layout.findViewById(R.id.city4);
        cityContainer4        = layout.findViewById(R.id.city4_name);
        weatherContainer4     = layout.findViewById(R.id.city4_weather);
        tempCurrentContainer4 = layout.findViewById(R.id.city4_temp);
        tempDayContainer4     = layout.findViewById(R.id.city4_temp_day);
        tempNightContainer4   = layout.findViewById(R.id.city4_temp_night);

        clickListener(city1, cityContainer1, weatherContainer1, tempCurrentContainer1, tempDayContainer1, tempNightContainer1);
        clickListener(city2, cityContainer2, weatherContainer2, tempCurrentContainer2, tempDayContainer2, tempNightContainer2);
        clickListener(city3, cityContainer3, weatherContainer3, tempCurrentContainer3, tempDayContainer3, tempNightContainer3);
        clickListener(city4, cityContainer4, weatherContainer4, tempCurrentContainer4, tempDayContainer4, tempNightContainer4);

        Parcel parcel = getParcel();

        if (parcel != null) {
            if (parcel.city != null) {
                cityContainer4.setText(parcel.city);
                weatherContainer4.setText(parcel.weather);
                tempCurrentContainer4.setText(parcel.tempCurrent);
                tempDayContainer4.setText(parcel.tempDay);
                tempNightContainer4.setText(parcel.tempNight);
            }
        }

        TextView addCity = layout.findViewById(R.id.add_city_start);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityAddition();
            }
        });

        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 2) {
//            city.setText(data.getStringExtra(CITY));
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);

            if (parcel != null) {
                cityContainer4        = getActivity().findViewById(R.id.city4_name);
                weatherContainer4     = getActivity().findViewById(R.id.city4_weather);
                tempCurrentContainer4 = getActivity().findViewById(R.id.city4_temp);
                tempDayContainer4     = getActivity().findViewById(R.id.city4_temp_day);
                tempNightContainer4   = getActivity().findViewById(R.id.city4_temp_night);

                if (parcel.city != null) cityContainer4.setText(parcel.city);
                if (parcel.weather != null) weatherContainer4.setText(parcel.weather);
                if (parcel.tempCurrent != null) tempCurrentContainer4.setText(parcel.tempCurrent);
                if (parcel.tempDay != null) tempDayContainer4.setText(parcel.tempDay);
                if (parcel.tempNight != null) tempNightContainer4.setText(parcel.tempNight);
            }
        }
    }

    static CitiesFragment create(Parcel parcel) {
        CitiesFragment f = new CitiesFragment();    // создание

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    private void clickListener(FrameLayout city, final TextView cityContainer, final TextView weatherContainer, final TextView tempCurrentContainer, final TextView tempDayContainer, final TextView tempNightContainer) {
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcel parcel = new Parcel();
                parcel.city        = cityContainer.getText().toString();
                parcel.weather     = weatherContainer.getText().toString();
                parcel.tempCurrent = tempCurrentContainer.getText().toString();
                parcel.tempDay     = tempDayContainer.getText().toString();
                parcel.tempNight   = tempNightContainer.getText().toString();

                showMain(parcel);
            }
        });
    }

    private void showMain(Parcel parcel) {
        if (isExistSecondView) {
            MainFragment main = MainFragment.create(parcel);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main, main);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            Intent intent = new Intent();
//            intent.putExtra(CITY, inputAddCity.getText().toString());
            intent.putExtra(CITY, parcel);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    }

    private void showCityAddition() {
        if (isExistSecondView) {
            CityAddFragment second = CityAddFragment.create(new Parcel());

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.second, second);
            ft.setTransition(FragmentTransaction. TRANSIT_FRAGMENT_FADE );
            ft.commit();
        } else {
            Intent intent = new Intent(getActivity(), CityAddActivity.class);
//            intent.putExtra(PARCEL, parcel);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private Parcel getParcel() {
        return (Parcel)((getArguments() != null) ? getArguments().getSerializable(PARCEL) : null);
    }
}
