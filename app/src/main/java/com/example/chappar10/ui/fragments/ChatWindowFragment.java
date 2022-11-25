package com.example.chappar10.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.MovableFloatingActionButton;
import com.example.chappar10.R;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Message;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.adapters.MessageAdapter;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChatWindowFragment extends Fragment {
    MainViewModel mainViewModel;
    MessageAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_chat_window, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the user from the bundle and display it
        TextView nickName = view.findViewById(R.id.tv_name);
        TextView status = view.findViewById(R.id.tv_status);
        TextView messageArea = view.findViewById(R.id.tv_message);
        MovableFloatingActionButton jokeButton = view.findViewById(R.id.joke_message);

        //Parameters
        Chat chat = (Chat) getArguments().getSerializable("chat");
        User user = (User) getArguments().getSerializable("user");

        //Values
        String myId = mainViewModel.getMyUserID();
        String receiverId = user != null ? user.getUid() : (chat.getReceiverId().equals(myId) ? chat.getSenderId() : chat.getReceiverId());

        //components
        EditText editText = view.findViewById(R.id.et_message);
        Button button = view.findViewById(R.id.btn_send);

        //Chat ID
        String chatId = mainViewModel.getChatId(myId, receiverId);

        RecyclerView messageList = view.findViewById(R.id.message_list);
        messageList.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        messageList.setLayoutManager(linearLayoutManager);
        adapter = new MessageAdapter(new ArrayList<>(), myId);
        messageList.setAdapter(adapter);

        //Ser Snackbar
        Snackbar snackbar = Snackbar.make(view, "Are you stuck for words? A joke is always a good way to start a conversation! press 💡", Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.show();

        //Ser the messages empty
        ArrayList<Message> messages = new ArrayList<>();
        adapter.setMessages(messages);

        //action send the message
        String finalReceiverId = receiverId;
        button.setOnClickListener(v -> {
            String messageText = editText.getText().toString();
            if (!messageText.isEmpty()) {
                // send the message
                mainViewModel.sendMessage(messageText, myId, finalReceiverId);
                editText.setText("");
            }
        });




        //make dissapear the joke button
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                snackbar.dismiss();
                jokeButton.setVisibility(View.GONE);
            } else {
                snackbar.show();
                jokeButton.setVisibility(View.VISIBLE);
            }
        });


        //TODO change this
        if (chat != null) {
            nickName.setText(chat.getSenderId());
        } else {
            nickName.setText(user.getNickname());
            status.setText("ONLINE");
        }

        CollectionReference db = FirebaseFirestore.getInstance().collection("chats").document(chatId).collection("messages");


        db.addSnapshotListener((value, error) -> {
            if (error != null) {
//                Toast.makeText(getContext(), "Error while loading!", Toast.LENGTH_SHORT).show();
                Log.w("TAG99", "Listen failed.", error);
                return;
            }
            if (value != null) {
                messages.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    document.getData();
                    Log.d("TAG99", document.getId() + " => " + document.getData());
                    Message message = document.toObject(Message.class);
                    messages.add(message);
                }
                adapter.setMessages(messages);
                messageList.scrollToPosition(messages.size() - 1);
            } else {
                Log.d("TAG99", "Current data: null");
            }
        });
    }
    }
