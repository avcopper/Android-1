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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityAddFragment extends Fragment implements Constants {
    boolean isExistSecondView;

    TextInputEditText inputAddCity;
    String newCity;
    Button buttonCityAdd;
    Pattern checkCity = Pattern.compile("^[а-яё]{2,}$");

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


//        inputAddCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus) return;
//                TextView tv = (TextView)v;
//                validate(tv, checkCity, "Это не город!");
//            }
//        });

        buttonCityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCity = inputAddCity.getText().toString();

                if (!newCity.isEmpty()) {
                    Boolean check = validate(inputAddCity, checkCity, "Проверьте введенные данные!");

                    if (check) {
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
            }
        });

//        ImageView back = view.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showCities(new Parcel());
//            }
//        });
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
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    }

    // Валидация
    private Boolean validate(TextView tv, Pattern check, String message){
        String value = tv.getText().toString();

        if (check.matcher(value).matches()){
            hideError(tv);
            return true;
        }
        else {
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
}
