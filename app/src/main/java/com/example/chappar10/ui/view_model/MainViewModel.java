package com.example.chappar10.ui.view_model;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.UsersDataRepository;
import com.example.chappar10.data.UserListLiveData;
import com.example.chappar10.data.UserLiveData;

public class MainViewModel extends AndroidViewModel {

    private final UsersDataRepository dataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = UsersDataRepository.getInstance();
    }

    public UserLiveData getUser(String uid) {
        return dataRepository.getUserLiveData(uid);
    }

    public UserListLiveData getUsers() {
        return dataRepository.getUsersListLiveData();
    }

//    public LiveData<List<Chat>> getChats() {
//        return dataRepository.getChatLiveData(FirebaseAuth.getInstance().getUid());
//    }
}
