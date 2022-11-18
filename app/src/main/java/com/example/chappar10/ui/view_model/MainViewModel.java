package com.example.chappar10.ui.view_model;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

    private final DataRepository dataRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = DataRepository.getInstance();
    }

    public ArrayList<User> getUsers() {
        return dataRepository.getUsers();
    }
}
