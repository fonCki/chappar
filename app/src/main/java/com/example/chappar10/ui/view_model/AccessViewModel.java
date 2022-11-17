package com.example.chappar10.ui.view_model;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.example.chappar10.ui.Activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class AccessViewModel extends AndroidViewModel {
    private final FirebaseAuth firebaseAuth;
    DataRepository dataRepository;


    public AccessViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        dataRepository = DataRepository.getInstance();
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("LoginViewModel", "login: success");
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        getApplication().startActivity(intent);
                    } else {
                        Log.d("LoginViewModel", "login: failure");
                        Toast.makeText(getApplication(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void createUser(String email, String password, String name, boolean isMale, String profileUrl, Date birthDate) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            User user = new User(firebaseAuth.getUid(), name, email, isMale, profileUrl, birthDate);
            dataRepository.addUser(user);
        });
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }



}
