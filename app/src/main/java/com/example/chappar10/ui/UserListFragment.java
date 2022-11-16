package com.example.chappar10.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.DataRepository;

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
        userList.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRepository = DataRepository.getInstance();

        adapter = new UsersAdapter(dataRepository.getUsers());

        userList.setAdapter(adapter);

    }
}