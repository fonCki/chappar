package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chappar10.R;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.Chat;
import com.example.chappar10.ui.adapters.ChatAdapters;
import com.example.chappar10.ui.view_model.MainViewModel;

import java.util.ArrayList;
import java.util.List;



public class ChatListFragment extends Fragment {
    RecyclerView chatList;
    ChatAdapters adapter;
    UsersDataRepository dataRepository;
    MainViewModel mainViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        chatList = view.findViewById(R.id.chat_list);
        chatList.hasFixedSize();
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRepository = UsersDataRepository.getInstance();
        adapter = new ChatAdapters(new ArrayList<>());
        String myUserID = mainViewModel.getMyUserID();


        adapter.setOnClickListener(chat -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("chat", chat);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_chat_window, bundle);
        });

        chatList.setAdapter(adapter);
        List<Chat> chats = new ArrayList<>();
        adapter.setChats(chats);

        mainViewModel.getChats(myUserID).observe(getViewLifecycleOwner(), chatsList -> {
            adapter.setChats(chatsList);
            adapter.notifyDataSetChanged();
        });

    }
}