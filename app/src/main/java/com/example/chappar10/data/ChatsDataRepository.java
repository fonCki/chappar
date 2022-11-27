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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatsDataRepository {
    private static ChatsDataRepository instance;
    private final CollectionReference chatsDBRef;
    private final MutableLiveData<List<Chat>> chatsLiveData;
    private final MutableLiveData<List<Message>> messagesLiveData;
    private MutableLiveData<Joke> jokeLiveData;


    private ChatsDataRepository() {
        chatsDBRef = FirebaseFirestore.getInstance().collection(PATH.CHATS);
        chatsLiveData = new MutableLiveData<>();
        messagesLiveData = new MutableLiveData<>();
        jokeLiveData = new MutableLiveData<>();
    }


    public static synchronized ChatsDataRepository getInstance() {
        if (instance == null) {
            synchronized (ChatsDataRepository.class) {
                if (instance == null) {
                    instance = new ChatsDataRepository();
                }
            }
        }
        return instance;
    }


    public Task sendMessage(Message message, String chatId, User user, User receiver) {
        Chat chat = new Chat(chatId, user, receiver, message);
        chatsDBRef.document(chatId).set(chat);
        return chatsDBRef.document(chatId).collection(PATH.MESSAGES).add(message);
    }


    //get all chats of the specific chat id
    public MutableLiveData<List<Message>> getMessages(String chatId) {
        Query query = chatsDBRef.document(chatId)
                      .collection(PATH.MESSAGES)
                      .orderBy(PATH.LAST_MESSAGE_TIME, Query.Direction.ASCENDING);

        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            List<Message> messages = new ArrayList<>();
            for (QueryDocumentSnapshot document : value) {
                messages.add(document.toObject(Message.class));
            }
            messagesLiveData.setValue(messages);
        });
        return messagesLiveData;
    }

    //get the chats list live data for the current user
    public MutableLiveData<List<Chat>> getChatsLiveData(String userId) {
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
                    chats.add(document.toObject(Chat.class));
                }
                chatsLiveData.setValue(chats);
            }
        });
        return chatsLiveData;
    }

    public MutableLiveData<Joke> getJoke() {
        jokeLiveData = new MutableLiveData<>();
        JokeApi jokeApi = ServiceGenerator.getJokeApi();
        Call<Joke> call = jokeApi.getJoke();
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    jokeLiveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
        return jokeLiveData;
    }
}
