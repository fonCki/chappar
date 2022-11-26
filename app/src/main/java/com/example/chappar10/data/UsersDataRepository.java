package com.example.chappar10.data;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.model.Location;
import com.example.chappar10.model.User;
import com.example.chappar10.utils.PATH;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class UsersDataRepository {
    private static UsersDataRepository instance;
    private MutableLiveData<List<User>> userListLiveData;
    private final CollectionReference usersDBRef;

    private StorageReference storageReference;

    private UsersDataRepository(){
        usersDBRef = FirebaseFirestore.getInstance().collection(PATH.USERS);
        storageReference = FirebaseStorage.getInstance().getReference().child(PATH.PROFILE_IMAGES);
        userListLiveData = new MutableLiveData<>();
        initUserListLiveData();
    }

    public static synchronized UsersDataRepository getInstance() {
        if (instance == null) {
            synchronized (UsersDataRepository.class) {
                if (instance == null) {
                    instance = new UsersDataRepository();
                }
            }
        }
        return instance;
    }

    //initialize the user list live in the repository
    private Task initUserListLiveData() {
        usersDBRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            List<User> users = value.toObjects(User.class);
            userListLiveData.setValue(users);
        });
        return null;
    }


    public void addLocation(String userId, Location location) {
        usersDBRef.document(userId).update(PATH.LOCATION, location);
    }

    public void addUser(User user) {
        usersDBRef.document(user.getUid()).set(user);
    }

    public void updateUser(User user) {
        usersDBRef.document(user.getUid()).set(user);
    }

    public void uploadProfilePicture(Uri uri, String uid) {
        storageReference.child(uid).child(PATH.PROFILE_IMAGES).putFile(uri).addOnSuccessListener(taskSnapshot -> {
            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uriFinal -> {
                usersDBRef.document(uid).update(PATH.PROFILE_URL, uriFinal.toString());
            });
        });
    }

    public void updateStatus(String userId, User.Status status) {
        usersDBRef.document(userId).update(PATH.STATUS, status);
    }

    //get the MutableLiveData List
    public MutableLiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    //get the user LiveData object
    public MutableLiveData<User> getUserLiveData(String userId) {
        AtomicReference<MutableLiveData<User>> userLiveData = new AtomicReference<>();
        //Get the user with the userId from the userListLiveData
        userListLiveData.getValue().stream().filter(user -> user.getUid().equals(userId)).findFirst().ifPresent(user -> {
            userLiveData.set(new MutableLiveData<>());
            userLiveData.get().setValue(user);
            return;
        });
        return userLiveData.get();
    }


}
