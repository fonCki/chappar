package com.example.chappar10.ui.fragments;

import static io.grpc.InternalConfigSelector.KEY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chappar10.R;
import com.example.chappar10.utils.KEYS;
import com.example.chappar10.utils.PATH;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

public class PreferencesFragment extends Fragment {
    Switch discoverySwitch, menSwitch, womenSwitch;
    TextView textDistance, agetTo, ageFrom;
    RangeSlider ageSlider;
    Slider distanceSlider;
    SharedPreferences preferences;
    Button saveButton, cancelButton;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_preferences, container, false);
        }

        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            discoverySwitch = view.findViewById(R.id.switch_discovery);
            menSwitch = view.findViewById(R.id.switch_man);
            womenSwitch = view.findViewById(R.id.switch_women);
            textDistance = view.findViewById(R.id.text_distance);
            agetTo = view.findViewById(R.id.age_to);
            ageFrom = view.findViewById(R.id.age_from);
            ageSlider = view.findViewById(R.id.range_seek_bar);
            distanceSlider = view.findViewById(R.id.discreteSlider);
            saveButton = view.findViewById(R.id.save_button);
            cancelButton = view.findViewById(R.id.cancel_button);

            preferences = getActivity().getSharedPreferences(PATH.PREFERENCES, Context.MODE_PRIVATE);

            discoverySwitch.setChecked(preferences.getBoolean(KEYS.DISCOVERY, false));
            menSwitch.setChecked(preferences.getBoolean(KEYS.SHOW_ME_MEN, false));
            womenSwitch.setChecked(preferences.getBoolean(KEYS.SHOW_ME_WOMEN, false));
            ageSlider.setValues(preferences.getFloat(KEYS.AGE_FROM, 18), preferences.getFloat(KEYS.AGE_TO, 99));
            distanceSlider.setValue(preferences.getFloat(KEYS.DISTANCE, 100));
            textDistance.setText(String.valueOf(preferences.getFloat(KEYS.DISTANCE, 100)));
            agetTo.setText(String.valueOf(preferences.getFloat(KEYS.AGE_TO, 99)));
            ageFrom.setText(String.valueOf(preferences.getFloat(KEYS.AGE_FROM, 18)));



            menSwitch.setOnClickListener(v -> {
                menSwitch.setText(menSwitch.isChecked() ? "Men 🔥" : "Men");
                });

            womenSwitch.setOnClickListener(v -> {
                womenSwitch.setText(womenSwitch.isChecked() ? "Women 🔥" : "Women");
                });


            //Distance Slider
            distanceSlider.addOnChangeListener((slider, value, fromUser) -> {
                //Set text without decimal
                textDistance.setText(String.valueOf((int) value) + " km");
            });

            //Age Slider
            ageSlider.addOnChangeListener((slider, value, fromUser) -> {

                float ageFromValue = ageSlider.getValues().get(0);
                float ageToValue = ageSlider.getValues().get(1);

                ageFrom.setText(String.valueOf((int) ageFromValue));
                agetTo.setText(String.valueOf((int) ageToValue));

            });

            //Save preferences
            saveButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(KEYS.DISCOVERY, discoverySwitch.isChecked());
                editor.putBoolean(KEYS.SHOW_ME_MEN, menSwitch.isChecked());
                editor.putBoolean(KEYS.SHOW_ME_WOMEN, womenSwitch.isChecked());
                editor.putFloat(KEYS.DISTANCE, distanceSlider.getValue());
                editor.putFloat(KEYS.AGE_FROM, ageSlider.getValues().get(0));
                editor.putFloat(KEYS.AGE_TO, ageSlider.getValues().get(1));
                Log.i("PREFERENCES", "Saved");
                editor.apply();

                //back to main
                getActivity().onBackPressed();

        });

            //Cancel preferences
            cancelButton.setOnClickListener(v -> {
                //back to main
                getActivity().onBackPressed();
            });

        }
    }

