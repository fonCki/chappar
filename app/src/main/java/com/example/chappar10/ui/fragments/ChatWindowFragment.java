package com.example.chappar10.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.example.chappar10.utils.SetImageTask;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindowFragment extends Fragment {
    MainViewModel mainViewModel;
    MessageAdapter adapter;

    TextView nickName;
    TextView status;
    TextView messageArea;
    CircleImageView avatar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_chat_window, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the user from the bundle and display it
        nickName = view.findViewById(R.id.tv_name);
        status = view.findViewById(R.id.tv_status);
        messageArea = view.findViewById(R.id.tv_message);
        avatar = view.findViewById(R.id.iv_profile_image);
        MovableFloatingActionButton jokeButton = view.findViewById(R.id.joke_message);

        //Setting 2 users involved in the chat
        String myUserID = mainViewModel.getMyUserID();
        AtomicReference<User> myUser = new AtomicReference<>(new User());
        mainViewModel.getUser(myUserID).observe(getViewLifecycleOwner(), user -> {
            myUser.set(user);
        });
        String otherUserID;


        AtomicReference<User> otherUser = new AtomicReference<>((User) getArguments().getSerializable("user"));
        Chat chat = (Chat) getArguments().getSerializable("chat");

        if (otherUser.get() == null) { // means that I came through the chat list
            otherUserID = chat.getChatId().replace(myUserID, ""); // the other user id is the chat id without my id
            mainViewModel.getUser(otherUserID).observeForever(user -> {
                if (user != null) otherUser.set(user);
                setData(otherUser.get());
            });
        }
        else { // means that I came through the user list
            otherUserID =  otherUser.get() == null ? "NO_FOUND" : otherUser.get().getUid();
            setData(otherUser.get());
        }

        //components
        EditText editText = view.findViewById(R.id.et_message);
        Button button = view.findViewById(R.id.btn_send);

        //Chat ID
        String chatId = mainViewModel.getChatId(myUserID, otherUserID);

        RecyclerView messageList = view.findViewById(R.id.message_list);
        messageList.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        messageList.setLayoutManager(linearLayoutManager);
        adapter = new MessageAdapter(new ArrayList<>(), myUserID);
        messageList.setAdapter(adapter);

        //Ser Snackbar
        Snackbar snackbar = Snackbar.make(view, "Are you stuck for words? A joke is always a good way to start a conversation! press 💡", Snackbar.ANIMATION_MODE_SLIDE);
        snackbar.setAnchorView(jokeButton);
        snackbar.show();

        //Ser the messages empty
        ArrayList<Message> messages = new ArrayList<>();
        adapter.setMessages(messages);

        //action send the message
        String finalReceiverId = otherUserID;
        button.setOnClickListener(v -> {
            String messageText = editText.getText().toString();
            if (!messageText.isEmpty()) {
                // send the message
                mainViewModel.sendMessage(messageText, myUser.get(), otherUser.get()); // I send the 2 users because I need to know the other user's name
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

        //Load the messages
        mainViewModel.getMessages(chatId).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("ChatWindowFragment", "Listen failed.", error);
                return;
            }
            if (value != null) {
                messages.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    document.getData();
                    Message message = document.toObject(Message.class);
                    messages.add(message);
                }
                adapter.setMessages(messages);
                messageList.scrollToPosition(messages.size() - 1);
            } else {
                Log.d("ChatWindowFragment", "Current data: null");
            }
        });
    }

    private void setData(User user) {
        nickName.setText(user.getNickname());
        status.setText(user.getStatus().toString());
        if (user.getProfileurl() != null) {
            new SetImageTask(avatar).execute(user.getProfileurl());
        }
    }
}
