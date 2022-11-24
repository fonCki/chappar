package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.chappar10.R;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.example.chappar10.utils.Distance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserDetailsFragment extends Fragment {

    MainViewModel viewModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //Declare components
        LinearLayout linearLayout = view.findViewById(R.id.match_tools);
        TextView nickName = view.findViewById(R.id.user_details_name);
        TextView location = view.findViewById(R.id.user_location);
        FloatingActionButton fab = view.findViewById(R.id.start_message);
        TextView bio = view.findViewById(R.id.user_bio);

        //set the visibility of the components
        linearLayout.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        bio.setVisibility(View.VISIBLE);



        // get the user from the bundle and display it
        User user = (User) getArguments().getSerializable("user");
        nickName.setText(user.getNickname());
        fab.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_chat_window, bundle);
        });

        viewModel.getUser(viewModel.getMyUserID()).observe(getViewLifecycleOwner(), user1 -> {
            // save distance as a string with 1 decimal place
            String distance = String.format("%.1f", Distance.GetDistance(user.getLocation(), user1.getLocation()));
            location.setText(distance + " km");
        });
    }
}