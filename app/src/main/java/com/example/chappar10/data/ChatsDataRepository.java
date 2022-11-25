package com.example.chappar10.data;



import com.example.chappar10.model.Chat;
import com.example.chappar10.model.Message;
import com.example.chappar10.utils.PATH;
import com.google.firebase.firestore.FirebaseFirestore;
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


    public void sendMessage(Message message, String chatId) {
        //TODO find a correct collection

        addChat(chatId, message);
    }


    private void addChat(String chatId, Message message) {
        chatsDBRef.collection(PATH.CHATS).document(chatId).set(new Chat(chatId, message.getSenderId(), message.getReceiverId(), message));
//        chatsDBRef.collection(PATH.CHATS).add(new Chat(chatId, message.getSenderId(), message.getReceiverId(), message));
        chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES).add(message);
    }

}
