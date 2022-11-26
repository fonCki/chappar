package com.example.chappar10.ui.view_model;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.data.AccessAuth;
import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.model.Location;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.activities.MainActivity;
import com.example.chappar10.utils.GPSData;
import com.google.android.gms.tasks.Task;
import java.util.Date;

public class AccessViewModel extends AndroidViewModel {
    private final AccessAuth accessAuth;
    private final UsersDataRepository usersDataRepository;
    private final Application application;
    private String myUserID;
    private MutableLiveData<User> myUserLiveData;


    public AccessViewModel(@NonNull Application application) {
        super(application);
        usersDataRepository = UsersDataRepository.getInstance();
        accessAuth = AccessAuth.getInstance();
        this.application = application;
    }

    public Task login(String email, String password) {
        return accessAuth.signIn(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) { //once the user is logged in
                        myUserID = task.getResult();
                        //Change the status of the user to online
                        usersDataRepository.updateStatus(myUserID, User.Status.ONLINE);
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
        return accessAuth.signUp(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                    myUserID = task.getResult();
                    User user = new User(myUserID, name, email, isMale,  birthDate);
                    usersDataRepository.addUser(user);
                    usersDataRepository.updateStatus(myUserID, User.Status.ONLINE);
                    if (uri != null) usersDataRepository.uploadProfilePicture(uri, myUserID);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(intent);
                } else {
                    Log.d("LoginViewModel", "createUser: failure");
                    Toast.makeText(getApplication(), "Create user failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

    public String getMyUserID() {
        return accessAuth.getMyUserID();
    }

    public MutableLiveData<User> getMyUserLiveData() {
        if (myUserLiveData == null) {
            myUserLiveData = new MutableLiveData<>();
            usersDataRepository.getUserLiveData(myUserID).observeForever(myUserLiveData::setValue);
        }
        return myUserLiveData;
    }

    public void updateLocation() {
        //TODO fix this hack
        Location location = GPSData.getCurrentLocation(application, accessAuth.getMyUserID(), usersDataRepository);
        //updated directly in the utils class
        Toast.makeText(application, "Location Updated", Toast.LENGTH_SHORT).show();
    }


    public void signOut() {
        usersDataRepository.updateStatus(accessAuth.getMyUserID(), User.Status.OFFLINE);
        accessAuth.signOut();
    }
}
