package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.ui.adapters.UsersAdapter;
import com.example.chappar10.ui.view_model.MainViewModel;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    RecyclerView userList;
    UsersAdapter adapter;


    MainViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        userList = view.findViewById(R.id.user_list);
        userList.hasFixedSize();
        userList.setLayoutManager(new GridLayoutManager(getContext(), 3));


        adapter = new UsersAdapter(new ArrayList<>());

        adapter.setOnClickListener(user -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_user_details, bundle);
        });
        userList.setAdapter(adapter);

        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            adapter.setUsers(users);
    });
    }

}