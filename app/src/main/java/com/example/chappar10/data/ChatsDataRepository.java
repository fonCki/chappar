package com.example.chappar10.data;



import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Message;
import com.example.chappar10.utils.PATH;
import com.google.firebase.firestore.FirebaseFirestore;


public class ChatsDataRepository {
    private static ChatsDataRepository instance;
    private final FirebaseFirestore chatsDBRef;


    private ChatsDataRepository() {
        chatsDBRef = FirebaseFirestore.getInstance();
    }
    public static ChatsDataRepository getInstance() {
        if (instance == null) {
            instance = new ChatsDataRepository();
        }
        return instance;
    }


    public void sendMessage(String messageText, String myUserID, String finalReceiverId) {
        String chatId = myUserID + finalReceiverId;
        Message message = new Message(messageText, myUserID, finalReceiverId);
        addChat(chatId, message);
    }

    private void addChat(String chatId, Message message) {
        chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES).add(message);
    }


}
