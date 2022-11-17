package com.example.chappar10.data;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private DatabaseReference myRef;
    private LiveData message;
    private ArrayList<User> users;


    private DataRepository(){
        initUsers();
    }

    private void initUsers() {
        // Create a list of 25 random users with real names, emails and age
        users = new ArrayList<>();
        for (int i = 0; i < 1005; i++) {
            User user = new User();
            user.setNickname("User " + (i + 1));
            user.setEmail("user" + (i + 1) + "@gmail.com");
            user.setAge((int) (Math.random() * 60 + 18));
            users.add(user);
        }

    }

    public static synchronized DataRepository getInstance() {
        if(instance == null)
            instance = new DataRepository();
        return instance;
    }

    public void init(String userId) {
        myRef = FirebaseDatabase.getInstance().getReference().child("message").child(userId);
        message = new LiveData(myRef);
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public LiveData getMessage() {
        return message;
    }

}
