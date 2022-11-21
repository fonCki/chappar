package com.example.chappar10.ui.view_model;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.Location;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.activities.MainActivity;
import com.example.chappar10.utils.GPSData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class AccessViewModel extends AndroidViewModel {
    private final FirebaseAuth firebaseAuth;
    UsersDataRepository dataRepository;
    Application application = getApplication();


    public AccessViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        dataRepository = UsersDataRepository.getInstance();
    }

    public Task login(String email, String password) {
       return firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { //once the user is logged in
                        Log.d("LoginViewModel", "login: success");
                        //Change the status of the user to online
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

    public Task createUser(String email, String password, String name, boolean isMale, Uri uri, Date birthDate) {
       return firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
           if (task.isSuccessful()) {
                    Log.d("LoginViewModel", "createUser: success");
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    User user = new User(userId, name, email, isMale,  birthDate);
                    dataRepository.addUser(user);
                    dataRepository.updateStatus(userId, User.Status.ONLINE);
                    if (uri != null) dataRepository.uploadProfilePicture(uri, userId);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    Log.d("LoginViewModel", "createUser: failure");
                    Toast.makeText(getApplication(), "Create user failed", Toast.LENGTH_SHORT).show();
                }
        });
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
