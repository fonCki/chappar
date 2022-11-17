package com.example.chappar10.ui.view_model;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.Location;
import com.example.chappar10.data.User;
import com.example.chappar10.ui.Activities.MainActivity;
import com.example.chappar10.utils.GPSData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class AccessViewModel extends AndroidViewModel {
    private final FirebaseAuth firebaseAuth;
    DataRepository dataRepository;
    Application application = getApplication();


    public AccessViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        dataRepository = DataRepository.getInstance();
    }

    public Task login(String email, String password) {
       return firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("LoginViewModel", "login: success");
                        dataRepository.updateStatus(firebaseAuth.getCurrentUser().getUid(), User.Status.ONLINE);
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplication().startActivity(intent);
                    } else {
                        Log.d("LoginViewModel", "login: failure");
                        Toast.makeText(getApplication(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public Task createUser(String email, String password, String name, boolean isMale, String profileUrl, Date birthDate) {
       return firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
                    Log.d("LoginViewModel", "createUser: success");
                    User user = new User(firebaseAuth.getCurrentUser().getUid(), name, email, isMale, profileUrl, birthDate);
                    dataRepository.addUser(user);
                    dataRepository.updateStatus(firebaseAuth.getCurrentUser().getUid(), User.Status.ONLINE);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    Log.d("LoginViewModel", "createUser: failure");
                    Toast.makeText(getApplication(), "Create user failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void updateLocation() {
        Location location = GPSData.getCurrentLocation(application, firebaseAuth.getUid(), dataRepository);
        //updated directly in the utils class
        Toast.makeText(application, "Location Updated", Toast.LENGTH_SHORT).show();
    }

    public void logout() {
        dataRepository.updateStatus(firebaseAuth.getUid(), User.Status.OFFLINE);
        firebaseAuth.signOut();
    }
}
