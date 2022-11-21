package com.example.chappar10.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chappar10.R;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.User;

public class ChatWindowFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_window, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the user from the bundle and display it
        TextView nickName = view.findViewById(R.id.tv_name);
        TextView status = view.findViewById(R.id.tv_status);
        TextView message = view.findViewById(R.id.tv_message);
        Chat chat = (Chat) getArguments().getSerializable("chat");
        User user = (User) getArguments().getSerializable("user");

        if (chat != null) {
            nickName.setText(chat.getSenderId());
            message.setText(chat.getLatestMessage().getMessage());
        }

        if (user != null) {
            nickName.setText(user.getNickname());
            status.setText("ONLINE");
        }
        }
    }
