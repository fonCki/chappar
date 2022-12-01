package com.example.chappar10.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chappar10.R;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

public class PreferencesFragment extends Fragment {
    Switch discoverySwitch, menSwitch, womenSwitch;
    TextView textDistance, agetTo, ageFrom;
    RangeSlider ageSlider;
    Slider distanceSlider;

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

            menSwitch.setOnClickListener(v -> {
                if (menSwitch.isChecked()) {
                    menSwitch.setChecked(true);
                    menSwitch.setTextColor(getResources().getColor(R.color.colorPrimary));
                    menSwitch.setText("Men 🔥");

                } else {
                    menSwitch.setChecked(false);
                    menSwitch.setTextColor(getResources().getColor(R.color.my_buble_message));
                    menSwitch.setText("Men");
                }
            });

            womenSwitch.setOnClickListener(v -> {
                if (womenSwitch.isChecked()) {
                    womenSwitch.setChecked(true);
                    womenSwitch.setTextColor(getResources().getColor(R.color.colorPrimary));
                    womenSwitch.setText("Women 🔥");
                } else {
                    womenSwitch.setChecked(false);
                    womenSwitch.setTextColor(getResources().getColor(R.color.my_buble_message));
                    womenSwitch.setText("Women");
                }
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



        }
}

