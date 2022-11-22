package com.example.chappar10.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chappar10.R;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class ChatWindowFragment extends Fragment {
    MainViewModel mainViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_chat_window, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the user from the bundle and display it
        TextView nickName = view.findViewById(R.id.tv_name);
        TextView status = view.findViewById(R.id.tv_status);
        TextView message = view.findViewById(R.id.tv_message);

        //Parameters
        Chat chat = (Chat) getArguments().getSerializable("chat");
        User user = (User) getArguments().getSerializable("user");

        //Values
        String myId = mainViewModel.getMyUserID();
        String receiverId = user != null ? user.getUid() : (chat.getReceiverId().equals(myId) ? chat.getSenderId() : chat.getReceiverId());

        //components
        EditText editText = view.findViewById(R.id.et_message);
        Button button = view.findViewById(R.id.btn_send);


        String finalReceiverId = receiverId;
        button.setOnClickListener(v -> {
            String messageText = editText.getText().toString();
            if (!messageText.isEmpty()) {
                // send the message
                mainViewModel.sendMessage(messageText, myId, finalReceiverId);
                editText.setText("");
            }
        });


        if (chat != null) {
            nickName.setText(chat.getSenderId());
            message.setText(chat.getLatestMessage().getMessage());
        } else {
            nickName.setText(user.getNickname());
            status.setText("ONLINE");
        }



        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("chats");

        Query query = collectionReference.whereEqualTo("senderId", mainViewModel.getMyUserID()).whereEqualTo("receiverId", receiverId);



        final DocumentReference docRef = FirebaseFirestore.getInstance().collection("chats").document("xkz8eT0qXDRTOW3WTLRYiHZ03S42").collection("messages").document("1AqN79rpRiAkwEVBDXu7");

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("TAG66", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("TAG66", "Current data: " + snapshot.getData());
                } else {
                    Log.d("TAG66", "Current data: null");
                }
            }
        });

        CollectionReference db = FirebaseFirestore.getInstance().collection("chats");


        db.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error while loading!", Toast.LENGTH_SHORT).show();
                Log.w("TAG66", "Listen failed.", error);
                return;
            }

            if (value != null) {
                for (DocumentSnapshot document : value.getDocuments()) {
                    Toast.makeText(getContext(), "Current data: " + document.getData(), Toast.LENGTH_SHORT).show();
                    Log.d("TAG66", document.getId() + " => " + document.getData());
                }
            } else {
                Toast.makeText(getContext(), "Current data: null", Toast.LENGTH_SHORT).show();
                Log.d("TAG66", "Current data: null");
            }
        });
    }
    }
