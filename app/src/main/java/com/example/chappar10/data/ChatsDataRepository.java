package com.example.chappar10.data;



import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Message;
import com.example.chappar10.model.User;
import com.example.chappar10.utils.PATH;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ChatsDataRepository {
    private static ChatsDataRepository instance;
    private final CollectionReference chatsDBRef;
    private MutableLiveData<List<Chat>> chatsLiveData;


    private ChatsDataRepository() {
        chatsDBRef = FirebaseFirestore.getInstance().collection(PATH.CHATS);
        chatsLiveData = new MutableLiveData<>();
    }


    public static ChatsDataRepository getInstance() {
        if (instance == null) {
            instance = new ChatsDataRepository();
        }
        return instance;
    }


    public void sendMessage(Message message, String chatId, User user, User receiver) {
        //TODO find a correct collection
        addMessage(chatId, message, user, receiver);
    }


    private Task addMessage(String chatId, Message message, User user, User receiver) {
        chatsDBRef.document(chatId).set(new Chat(chatId, user, receiver, message));
        return chatsDBRef.document(chatId).collection(PATH.MESSAGES).add(message);
    }

    public Task getChats(String userId) {
        List<Chat> chats = new ArrayList<>();
        Query query = chatsDBRef.whereArrayContains(PATH.INVOLVED_USERS, userId)
                        .orderBy(PATH.LAST_MESSAGE_TIME, Query.Direction.DESCENDING);
        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            if (value != null) {
                chats.clear();
                for (QueryDocumentSnapshot document : value) {
                    Log.i("TAG", "getChats: " + document.getData());
                    chats.add(document.toObject(Chat.class));
                }
                chatsLiveData.setValue(chats);
            }
        });
        return query.get();
    }

    public Query getMessages(String chatId) {
        return chatsDBRef.document(chatId).collection(PATH.MESSAGES);
    }

    public MutableLiveData<List<Chat>> getChatsLiveData(String userId) {
        // wait for getChats(userId) and then return chatsLiveData
        getChats(userId);
        return chatsLiveData;
    }
}
