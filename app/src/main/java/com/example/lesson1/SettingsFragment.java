package com.example.lesson1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

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

    static SettingsFragment create(Parcel parcel) {
        SettingsFragment f = new SettingsFragment();

        Bundle args = new Bundle();
        args.putSerializable(PARCEL, parcel);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isExistSecondView = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ;

        humidityContainer = view.findViewById(R.id.settings_humidity);
        windContainer = view.findViewById(R.id.settings_wind);

        checkState();

        if (isExistSecondView) {
            humidityContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcel parcel = new Parcel();
                    parcel.humidityVisibility = humidityContainer.isChecked() ? 1 : -1;
                    parcel.windVisibility = windContainer.isChecked() ? 1 : -1;
                    showMain(parcel);
                }
            });

            windContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Parcel parcel = new Parcel();
                    parcel.humidityVisibility = humidityContainer.isChecked() ? 1 : -1;
                    parcel.windVisibility = windContainer.isChecked() ? 1 : -1;
                    showMain(parcel);
                }
            });
        }
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

            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.setResult(RESULT_OK, intent);
                activity.finish();
            }
        }
    }

    private Parcel getParcel() {
        return (Parcel)((getArguments() != null) ? getArguments().getSerializable(PARCEL) : null);
    }

    private void checkState() {
        Parcel parcel = getParcel();

        if (parcel != null) {
            if (parcel.humidityVisibility == -1) {
                humidityContainer.setChecked(false);
            } else if (parcel.humidityVisibility == 1) {
                humidityContainer.setChecked(true);
            }

            if (parcel.windVisibility == -1) {
                windContainer.setChecked(false);
            } else if (parcel.windVisibility == 1) {
                windContainer.setChecked(true);
            }
        }
    }
}
