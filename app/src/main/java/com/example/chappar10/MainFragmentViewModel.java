package com.example.chappar10;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.Message;
import com.example.chappar10.data.UserRepository;
import com.example.chappar10.data.UsersLiveData;

public class MainFragmentViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final DataRepository dataRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(application);
        dataRepository = DataRepository.getInstance();
    }

    public void init() {
        String userId = userRepository.getCurrentUser().getUid();
        dataRepository.init(userId);
    }

    public void saveMessage(String message) {
        dataRepository.saveMessage(message);
    }

    public LiveData<Message> getMessage() {
        return dataRepository.getMessage();
    }

    public UsersLiveData getUsers() {
        return dataRepository.getUsers();
//        return null;
    }

}
