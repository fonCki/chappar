package com.example.chappar10.ui.view_model;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.data.ChatsDataRepository;
import com.example.chappar10.data.api.Joke;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Location;
import com.example.chappar10.model.Message;
import com.example.chappar10.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final UsersDataRepository usersDataRepository;
    private final ChatsDataRepository chatsDataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.chatsDataRepository = ChatsDataRepository.getInstance();
        this.usersDataRepository = UsersDataRepository.getInstance();
    }

    public MutableLiveData<User> getUser(String uid) {
        return usersDataRepository.getUserLiveData(uid);
    }

    public MutableLiveData<List<User>> getUsers() {
        return usersDataRepository.getUserListLiveData();
    }

    //Virgin users are users that have not been liked or disliked by the current user
    public MutableLiveData<List<User>> getViginUsers() {
        return usersDataRepository.getVirginUsers(getMyUserID());
    }


    public String getMyUserID() {
        return FirebaseAuth.getInstance().getUid();
    }

    public void sendMessage(String messageText, User myUser, User finalReceiver) {
        Message message = new Message(messageText, myUser.getUid(), finalReceiver.getUid());
        chatsDataRepository.sendMessage(message, getChatId(message), myUser, finalReceiver);
    }

    public String getChatId(Message message) {
        return getChatId(message.getSenderId(), message.getReceiverId());
    }

    public String getChatId(String senderId, String receiverId) {
        return (senderId.compareTo(receiverId) > 0) ?
                senderId + receiverId :
                receiverId + senderId;
    }

    public Location getLocation() {
        return getUser(getMyUserID()).getValue().getLocation();
    }

    public void like(String userId) {
        usersDataRepository.like(getMyUserID(), userId);
    }

    public void dislike(String userId) {
        usersDataRepository.dislike(getMyUserID(), userId);
    }

    // given a String user ID calls to getChats in ChatsDataRepository
    public MutableLiveData<List<Chat>> getChats(String userId) {
        return chatsDataRepository.getChatsLiveData(userId);
    }

    public MutableLiveData<List<Message>> getMessages(String chatId) {
        return chatsDataRepository.getMessages(chatId);
    }

    public MutableLiveData<Joke> getJoke() {
        return chatsDataRepository.getJoke();
    }


    public MutableLiveData<List<User>> getMatchesList() {
        return usersDataRepository.getMatchesListLiveData(getMyUserID());
    }
}