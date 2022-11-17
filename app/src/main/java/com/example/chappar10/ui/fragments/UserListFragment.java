package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.example.chappar10.ui.UsersAdapter;

public class UserListFragment extends Fragment {

    RecyclerView userList;
    UsersAdapter adapter;

    DataRepository dataRepository;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        userList = view.findViewById(R.id.user_list);
        userList.hasFixedSize();
        userList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dataRepository = DataRepository.getInstance();

        adapter = new UsersAdapter(dataRepository.getUsers());

        adapter.setOnClickListener(user -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_user_details, bundle);
        });
        userList.setAdapter(adapter);





    }

    private void move(User user) {
        // move to another fragment

    }
}