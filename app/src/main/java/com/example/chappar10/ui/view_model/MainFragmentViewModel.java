package com.example.chappar10.ui.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.UsersDataRepository;

public class MainFragmentViewModel extends AndroidViewModel {

//    private final UserRepository userRepository;
    private final UsersDataRepository dataRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
//        userRepository = UserRepository.getInstance(application);
        dataRepository = UsersDataRepository.getInstance();
    }

//    public void init() {
////        String userId = userRepository.getCurrentUser().getUid();
//        dataRepository.init(FirebaseAuth.getInstance().getCurrentUser().getUid());
//    }

//    public void saveMessage(String message) {
//        dataRepository.saveMessage(message);
//    }

//    public LiveData<Message> getMessage() {
//        return dataRepository.getMessageLiveData();
//    }

}
