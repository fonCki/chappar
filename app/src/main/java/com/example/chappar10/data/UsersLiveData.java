package com.example.chappar10.data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class UsersLiveData extends LiveData<User> {
    private final ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Toast.makeText(null, "onChildAdded", Toast.LENGTH_SHORT).show();
            User user = snapshot.getValue(User.class);
            setValue(user);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Toast.makeText(null, "onChildChanged", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Toast.makeText(null, "onChildRemoved", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Toast.makeText(null, "onChildMoved", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(null, "onCancelled", Toast.LENGTH_SHORT).show();
        }
    };

    DatabaseReference databaseReference;

    public UsersLiveData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

}
