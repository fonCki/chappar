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


    public void sendMessage(Message message) {
        //TODO find a correct collection
        String chatId = GetChatIdOrGenerate(message.getSenderId(), message.getReceiverId());
        addChat(chatId, message);
    }

    private String GetChatIdOrGenerate(String senderId, String receiverId) {
        //TODO find a correct collection
        String firstSolution = senderId + receiverId;
        AtomicReference<String> solution = new AtomicReference<>("");

        chatsDBRef.collection(PATH.CHATS).document(firstSolution).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    solution.set(firstSolution);
                    return;
                }
            }
        });
        if (solution.get() != "") {
            return solution.get();
        }

        String secondSolution = receiverId + senderId;

        //TODO I CAN AVOID ALL THIS, IF IS NOT ONE IS ANOTHER
        solution.set(secondSolution);
        chatsDBRef.collection(PATH.CHATS).document(secondSolution).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    solution.set(secondSolution);
                    return;
                }
            }
        });
        return solution.get();
    }

    private void addChat(String chatId, Message message) {
        chatsDBRef.collection(PATH.CHATS).document(chatId).collection(PATH.MESSAGES).add(message);
    }



}
