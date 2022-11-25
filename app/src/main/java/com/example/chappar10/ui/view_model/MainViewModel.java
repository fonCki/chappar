package com.example.chappar10.ui.view_model;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.ChatsDataRepository;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.data.UserListLiveData;
import com.example.chappar10.data.UserLiveData;
import com.example.chappar10.model.Location;
import com.example.chappar10.model.Message;
import com.example.chappar10.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.net.URL;

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

    public void sendMessage(String messageText, User myUser, User finalReceiver) {
        Message message = new Message(messageText, myUser.getUid(), finalReceiver.getUid());
        Log.i("TAG", "sendMessage: " + message.getMessage() + message.getSenderId() + message.getReceiverId());
        Log.i("TAG", "sendMessage2: " + myUser.getUid() + finalReceiver.getUid());
        chatsDataRepository.sendMessage(message, getChatId(message), myUser, finalReceiver);
    }

    public String getChatId(Message message){
        return getChatId(message.getSenderId(), message.getReceiverId());
    }

    public String getChatId(String senderId, String receiverId){
        return (senderId.compareTo(receiverId) > 0) ?
                senderId + receiverId :
                receiverId + senderId;
    }
    public Location getLocation() {
        return getUser(getMyUserID()).getValue().getLocation();
    }

    public void like(String userId) {
        dataRepository.like(getMyUserID(), userId);
    }

    public void dislike(String userId) {
        dataRepository.dislike(getMyUserID(), userId);
    }


    //Giving a chatId, this method will return the other user's UID
    public String getReceiverName(String chatId) {
        Log.d("MainViewModel", "getReceiverName: " + chatId);
        String myId = getMyUserID();
        String receiverId = chatId.replace(myId, "");
        Log.d("MainViewModel", "getReceiverName: " + receiverId);
//        User user = dataRepository.getUser(receiverId);
//        Log.i("MainViewModel", "getReceiverName: " + user.getNickname());
        return "user.getNickname()";
    }

    // given a String user ID calls to getChats in ChatsDataRepository
    public Query getChats(String userId) {
        return chatsDataRepository.getChats(userId);
    }

    public Query getMessages(String chatId) {
        return chatsDataRepository.getMessages(chatId);
    }
}
