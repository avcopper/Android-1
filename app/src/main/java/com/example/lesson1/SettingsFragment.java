package com.example.lesson1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements Constants {
    private boolean isExistSecondView;

    private CheckBox humidityContainer;
    private CheckBox windContainer;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_settings, container, false);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ;

        ImageView back = layout.findViewById(R.id.back);
        humidityContainer = layout.findViewById(R.id.settings_humidity);
        windContainer = layout.findViewById(R.id.settings_wind);

        if (isExistSecondView) {
            humidityContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcel parcel = new Parcel();
                    parcel.humidity = humidityContainer.isChecked() ? 1 : -1;
                    parcel.wind = windContainer.isChecked() ? 1 : -1;
                    showMain(parcel);
                }
            });

            windContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcel parcel = new Parcel();
                    parcel.humidity = humidityContainer.isChecked() ? 1 : -1;
                    parcel.wind = windContainer.isChecked() ? 1 : -1;
                    showMain(parcel);
                }
            });
        } else {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcel parcel = new Parcel();
                    parcel.humidity = humidityContainer.isChecked() ? 1 : -1;
                    parcel.wind = windContainer.isChecked() ? 1 : -1;
                    showMain(parcel);
                }
            });
        }

        return layout;
    }

    static SettingsFragment create(Parcel parcel) {
        SettingsFragment f = new SettingsFragment();

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
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
}
