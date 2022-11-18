package com.example.chappar10.data;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
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
        // Create a list of 25 random users with real String UID, String nickname, String email, boolean isMale, String profileurl, Date birthDate
        users = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            User user = new User();
            user.setUid("uid" + i);
            user.setNickname("nickname" + i);
            user.setEmail("email" + i);
            user.setMale(i % 2 == 0);
            user.setProfileurl("profileurl" + i);
            user.setBirthDate(new Date());
            user.setLocation(generateRandomCorrdinates());
            users.add(user);
        }
    }

    private Location generateRandomCorrdinates() {
        // Generate random coordinates
        double lat = Math.random() * 180 - 90;
        double lng = Math.random() * 360 - 180;
        return new Location(lat, lng);
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
        init(userId);
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("location").push().setValue(location);
    }

    public boolean addUser(User user) {
        final boolean[] added = {false};
        FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                added[0] = task.isSuccessful();
            }
        });
        return added[0];
    }

    public void updateStatus(String userId, User.Status status) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("status").setValue(status);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public LiveData getMessage() {
        return message;
    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getUid().equals(userId)) {
                return user;
            }
        }
        return null;
    }

}
