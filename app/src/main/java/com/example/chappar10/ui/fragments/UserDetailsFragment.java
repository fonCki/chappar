package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chappar10.R;
import com.example.chappar10.data.User;

public class UserDetailsFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the user from the bundle and display it
        TextView nickName = view.findViewById(R.id.user_details);
        TextView location = view.findViewById(R.id.user_location);
        User user = (User) getArguments().getSerializable("user");
        nickName.setText(user.getNickname());
        location.setText(user.getLocation().latitude + ", " + user.getLocation().longitude);
    }
}