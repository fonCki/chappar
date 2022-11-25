package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.adapters.ChatAdapters;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment {
    RecyclerView chatList;
    ChatAdapters adapter;
    List<Chat> chats;

    UsersDataRepository dataRepository;

    MainViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_chat_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        chatList = view.findViewById(R.id.chat_list);
        chatList.hasFixedSize();
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRepository = UsersDataRepository.getInstance();
        adapter = new ChatAdapters(new ArrayList<>());


        adapter.setOnClickListener(chat -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("chat", chat);
//            Toast.makeText(getContext(), "Message: " + chat.getSenderId(), Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_chat_window, bundle);
        });

        chatList.setAdapter(adapter);
        chats = new ArrayList<>();
        adapter.setChats(chats);


        CollectionReference db = FirebaseFirestore.getInstance().collection("chats");

        db.addSnapshotListener((value, error) -> {
            if (error != null) {
//                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("TAG123", "onViewCreated: " );
                return;
            }
            if (value != null) {
                chats.clear();
                for (Chat chat : value.toObjects(Chat.class)) {
                    Log.i("TAG123", "onViewCreated: " + chat.getLastMessage());
                    chat.setName(viewModel.getReceiverName(chat.getChatId()));
                    chats.add(chat);
                }
                adapter.setChats(chats);
            }
        });
    }
}