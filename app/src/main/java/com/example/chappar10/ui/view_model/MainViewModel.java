package com.example.chappar10.ui.view_model;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.ChatsDataRepository;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.data.UserListLiveData;
import com.example.chappar10.data.UserLiveData;
import com.example.chappar10.model.Message;
import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends AndroidViewModel {



    private final UsersDataRepository dataRepository;
    private final ChatsDataRepository chatsDataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.chatsDataRepository = ChatsDataRepository.getInstance();
        this.dataRepository = UsersDataRepository.getInstance();
    }

    public UserLiveData getUser(String uid) {
        return dataRepository.getUserLiveData(uid);
    }

    public UserListLiveData getUsers() {
        return dataRepository.getUsersListLiveData();
    }


    public String getMyUserID(){
        // TODO delete implementation Auth
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void sendMessage(String messageText, String myUserID, String finalReceiverId) {
        Message message = new Message(messageText, myUserID, finalReceiverId);
        chatsDataRepository.sendMessage(message);
    }
}
