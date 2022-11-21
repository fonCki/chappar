package com.example.chappar10.ui.fragments;


import android.os.Bundle;
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
import com.example.chappar10.ui.adapters.ChatAdapters;

public class ChatListFragment extends Fragment {
    RecyclerView chatList;
    ChatAdapters adapter;

    DataRepository dataRepository;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        chatList = view.findViewById(R.id.chat_list);
        chatList.hasFixedSize();
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRepository = DataRepository.getInstance();
        adapter = new ChatAdapters(dataRepository.getChats());
        chatList.setAdapter(adapter);

        adapter.setOnClickListener(chat -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("chat", chat);
            Toast.makeText(getContext(), "Message: " + chat.getSenderId(), Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_chat_window, bundle);
        });
    }
}