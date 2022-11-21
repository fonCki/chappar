package com.example.chappar10.data;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLiveData extends LiveData<User> {
    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
            User user = snapshot.getValue(User.class);
            setValue(user);
        }

        @Override
        public void onCancelled(com.google.firebase.database.DatabaseError error) {
        }
    };

    DatabaseReference userReference;
    public UserLiveData(String userId) {
        userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
    }

    @Override
    protected void onActive() {
        super.onActive();
        userReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        userReference.removeEventListener(listener);
    }
}
