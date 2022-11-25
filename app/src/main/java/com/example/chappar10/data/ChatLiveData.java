package com.example.chappar10.data;

import androidx.annotation.NonNull;

import com.example.chappar10.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;

public class ChatLiveData extends androidx.lifecycle.LiveData<List<Chat>> {
    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            List<Chat> chats = new ArrayList<>();
            for (DataSnapshot child : snapshot.getChildren()) {
                Chat chat = child.getValue(Chat.class);
                chats.add(chat);
            }
            setValue(chats);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    DatabaseReference databaseReference;

    public ChatLiveData(String userId) {
//        databaseReference = FirebaseDatabase.getInstance().getReference("chats").child(userId).child("chats");
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }

}
