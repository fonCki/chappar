package com.example.chappar10.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataRepository {
    private static DataRepository instance;
    private DatabaseReference myRef;
    private LiveData message;
    private UsersLiveData users;

    private DataRepository(){}

    public static synchronized DataRepository getInstance() {
        if(instance == null)
            instance = new DataRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("message").child(userId);
        message = new LiveData(myRef);
        users = new UsersLiveData();
    }

    public void saveMessage(String message) {
        myRef.setValue(new Message(message));
    }

    public void addLocation(String userId, Location location) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("locations").push().setValue(location);
    }

    public boolean addUser(String userId, User user) {
        final boolean[] added = {false};
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                added[0] = task.isSuccessful();
            }
        });
        return added[0];
    }

    public UsersLiveData getUsers() {
        return users;
    }

    public LiveData getMessage() {
        return message;
    }

}
