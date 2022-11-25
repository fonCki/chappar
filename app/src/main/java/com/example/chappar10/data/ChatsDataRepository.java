package com.example.chappar10.data;



import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Message;
import com.example.chappar10.model.User;
import com.example.chappar10.utils.PATH;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


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


    public void sendMessage(Message message, String chatId, User user, User receiver) {
        //TODO find a correct collection

        addMessage(chatId, message, user, receiver);
    }


    private Task addMessage(String chatId, Message message, User user, User receiver) {
        chatsDBRef.collection(PATH.CHATS).document(chatId).set(new Chat(chatId, user, receiver, message));
        return chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES).add(message);
    }

    public Query getChats(String userId) {
        return chatsDBRef.collection(PATH.CHATS)
                .whereArrayContains(PATH.INVOLVED_USERS, userId)
                .orderBy(PATH.LAST_MESSAGE_TIME, Query.Direction.DESCENDING);
    }

    public Query getMessages(String chatId) {
        return chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES);
    }
}
