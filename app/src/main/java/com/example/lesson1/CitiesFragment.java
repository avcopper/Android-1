package com.example.lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesFragment extends Fragment implements Constants {
    private final static int REQUEST_CODE = 2;
    boolean isExistSecondView;

    public CitiesFragment() {
    }

    static CitiesFragment create(Parcel parcel) {
        CitiesFragment f = new CitiesFragment();    // создание

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        TextView addCity = view.findViewById(R.id.add_city_start);
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityAddition();
            }
        });

//        ImageView back = view.findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMain(new Parcel());
//            }
//        });

        Parcel parcel = new Parcel();
        parcel.cities = getResources().getStringArray(R.array.cities_collection);
        parcel.tempCities = getResources().getStringArray(R.array.temperature_collection);
        parcel.weather_collection = getResources().getStringArray(R.array.weather_collection);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_city);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view, LinearLayoutManager.VERTICAL, false));
        RecyclerAdapterCity adapter = new RecyclerAdapterCity(parcel);

        adapter.setClickListener(new RecyclerAdapterCity.RecyclerItemClickListener() {
            @Override
            public void onItemClick(Parcel parcel) {
                Intent intent = new Intent();
                intent.putExtra(CITY, parcel);
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1 && requestCode == 2) {
//            city.setText(data.getStringExtra(CITY));
            Parcel parcel = (Parcel)data.getExtras().getSerializable(CITY);

            if (parcel != null) {
                if (parcel.city != null) {
                    String[] cities = getResources().getStringArray(R.array.cities_collection);
                    parcel.cities = Arrays.copyOf(cities, cities.length + 1);
                    parcel.cities[cities.length] = parcel.city;
                    parcel.tempCities = getResources().getStringArray(R.array.temperature_collection);
                    parcel.weather_collection = getResources().getStringArray(R.array.weather_collection);

                    RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view_city);
                    recyclerView.setHasFixedSize(true);
//                recyclerView.setLayoutManager(new LinearLayoutManager(view, LinearLayoutManager.VERTICAL, false));
                    RecyclerAdapterCity adapter = new RecyclerAdapterCity(parcel);
                    recyclerView.setAdapter(adapter);

                    adapter.setClickListener(new RecyclerAdapterCity.RecyclerItemClickListener() {
                        @Override
                        public void onItemClick(Parcel parcel) {
                            Intent intent = new Intent();
                            intent.putExtra(CITY, parcel);
                            getActivity().setResult(RESULT_OK, intent);
                            getActivity().finish();
                        }
                    });
                }
            }
        }
    }

    private void showMain(Parcel parcel) {
        if (isExistSecondView) {
            MainFragment main = MainFragment.create(parcel);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.activity_main_land_main, main);
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
            ft.replace(R.id.activity_main_land_second, second);
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

//    private void checkParcel() {
//        Parcel parcel = getParcel();
//
//        if (parcel != null) {
//            if (parcel.city != null) {
////                cityContainer4.setText(parcel.city);
////                weatherContainer4.setText(parcel.weather);
////                tempCurrentContainer4.setText(parcel.tempCurrent);
////                tempDayContainer4.setText(parcel.tempDay);
////                tempNightContainer4.setText(parcel.tempNight);
//            }
//        }
//    }

//    private void clickListener(FrameLayout city, final TextView cityContainer, final TextView weatherContainer, final TextView tempCurrentContainer, final TextView tempDayContainer, final TextView tempNightContainer) {
//        city.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Parcel parcel = new Parcel();
////                parcel.city        = cityContainer.getText().toString();
////                parcel.weather     = weatherContainer.getText().toString();
////                parcel.tempCurrent = tempCurrentContainer.getText().toString();
////                parcel.tempDay     = tempDayContainer.getText().toString();
////                parcel.tempNight   = tempNightContainer.getText().toString();
//
//                showMain(parcel);
//            }
//        });
//    }
}
