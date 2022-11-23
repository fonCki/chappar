package com.example.chappar10.data;



import android.util.Log;

import com.example.chappar10.model.Message;
import com.example.chappar10.utils.PATH;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicReference;


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


    public void sendMessage(Message message, String chatId) {
        //TODO find a correct collection

        addChat(chatId, message);
    }


    private void addChat(String chatId, Message message) {
        chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES).add(message);
    }



}
