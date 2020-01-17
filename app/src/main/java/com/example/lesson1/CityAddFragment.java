package com.example.lesson1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityAddFragment extends Fragment implements Constants {
    boolean isExistSecondView;

    EditText inputAddCity;
    String newCity;
    Button buttonCityAdd;

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
        buttonCityAdd = view.findViewById(R.id.button_add_city);

        buttonCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCity = inputAddCity.getText().toString();

                if (!newCity.isEmpty()) {
                    DataHandler dataHandler = new DataHandler();
                    int tempRandom = dataHandler.generateNumber(-40, 40);
                    int weather    = dataHandler.generateNumber(0, 4);

                    Parcel parcel = new Parcel();
                    parcel.city = inputAddCity.getText().toString();
                    parcel.weather = weathterList[weather];
                    parcel.tempCurrent = (tempRandom > 0 ? "+" : "") + tempRandom + "\u00B0";
                    parcel.tempDay = ((tempRandom + 3) > 0 ? "+" : "") + (tempRandom + 3) + "\u00B0";
                    parcel.tempNight = ((tempRandom - 3) > 0 ? "+" : "") + (tempRandom - 3) + "\u00B0";

                    showCities(parcel);
                }
            }
        });

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(new Parcel());
            }
        });
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
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    }
}
