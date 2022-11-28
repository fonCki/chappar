package com.example.chappar10.data;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.chappar10.model.Location;
import com.example.chappar10.model.UID;
import com.example.chappar10.model.User;
import com.example.chappar10.utils.PATH;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.local.ReferenceSet;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsersDataRepository {
    private static AccessAuth accessAuth;
    private static UsersDataRepository instance;
    private final CollectionReference usersDBRef;
    private final MutableLiveData<List<User>> fullUserListLiveData;
    private final MutableLiveData<List<User>> virginUserListLiveData;

    private  MutableLiveData<Set<String>> likesReceivedLiveData;
    private  MutableLiveData<Set<String>> likesSentLiveData;
    private  MutableLiveData<Set<String>> matchesLiveData;
    private  MutableLiveData<List<User>> matchesListLiveData;





    private final StorageReference storageReference;

    private UsersDataRepository(){
        accessAuth = AccessAuth.getInstance();
        usersDBRef = FirebaseFirestore.getInstance().collection(PATH.USERS);
        storageReference = FirebaseStorage.getInstance().getReference().child(PATH.PROFILE_IMAGES);
        fullUserListLiveData = new MutableLiveData<>();
        virginUserListLiveData = new MutableLiveData<>();
        likesReceivedLiveData = new MutableLiveData<>();
        likesSentLiveData = new MutableLiveData<>();
        matchesLiveData = new MutableLiveData<>();
        matchesListLiveData = new MutableLiveData<>();
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
            fullUserListLiveData.setValue(users);
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
        return fullUserListLiveData;
    }

    //get the user LiveData object
    public MutableLiveData<User> getUserLiveData(String userId) {
        AtomicReference<MutableLiveData<User>> userLiveData = new AtomicReference<>();
        //Get the user with the userId from the userListLiveData
        fullUserListLiveData.getValue().stream().filter(user -> user.getUid().equals(userId)).findFirst().ifPresent(user -> {
            userLiveData.set(new MutableLiveData<>());
            userLiveData.get().setValue(user);
            return;
        });
        return userLiveData.get();
    }


    public MutableLiveData<List<User>> getVirginUsers(String userId) {
        usersDBRef.document(userId).collection(PATH.LIKES_SENT).addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            //create a copy of the fullUserListLiveData I know is not the best way to do it but it works FIX PLEASE
            List<User> fullList = new ArrayList<>(fullUserListLiveData.getValue());
            List<UID> likedUsers = value.toObjects(UID.class);
            for (UID likedUser : likedUsers) {
                fullList.removeIf(user -> user.getUid().equals(likedUser.getUid()) || (user.getUid().equals(userId)));
            }
            virginUserListLiveData.setValue(fullList);
        });
        return virginUserListLiveData;
    }

    public void like(String myUserID, String userId) {
        usersDBRef.document(userId).collection(PATH.LIKES_RECEIVED).document(myUserID).set(new UID(myUserID));
        usersDBRef.document(myUserID).collection(PATH.LIKES_SENT).document(userId).set(new UID(userId));
//        usersDBRef.document(userId).update(PATH.LIKES_RECEIVED, FieldValue.arrayUnion(myUserID));
//        usersDBRef.document(myUserID).update(PATH.LIKES_SENT, FieldValue.arrayUnion(userId));
    }


    public void dislike(String myUserID, String userId) {
//        usersDBRef.document(userId).update(PATH.LIKES_RECEIVED, FieldValue.arrayRemove(myUserID));
//        usersDBRef.document(myUserID).update(PATH.LIKES_SENT, FieldValue.arrayRemove(userId));
        usersDBRef.document(userId).collection(PATH.LIKES_RECEIVED).whereEqualTo("uid", myUserID).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                usersDBRef.document(userId).collection(PATH.LIKES_RECEIVED).document(queryDocumentSnapshots.getDocuments().get(i).getId()).delete();
            }
        });
        usersDBRef.document(myUserID).collection(PATH.LIKES_SENT).whereEqualTo("uid", userId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                usersDBRef.document(myUserID).collection(PATH.LIKES_SENT).document(queryDocumentSnapshots.getDocuments().get(i).getId()).delete();
            }
        });
    }

    public MutableLiveData<List<User>> getMatchesListLiveData(String myUserID) {
        matchesListLiveData = new MutableLiveData<>();
        //update the lists
        getMatchesLiveData(myUserID);
        getLikesReceivedLiveData(myUserID);
        getLikesSentLiveData(myUserID);

        //observe the matches list
        matchesLiveData.observeForever(matches -> {
            List<User> matchesList = new ArrayList<>();
            for (String match : matches) {
                for (User user : fullUserListLiveData.getValue()) {
                    if (user.getUid().equals(match)) {
                        matchesList.add(user);
                    }
                }
            }
            matchesListLiveData.setValue(matchesList);
        });
        return matchesListLiveData;
    }

    private MutableLiveData<Set<String>> getLikesReceivedLiveData(String userId) {
        likesReceivedLiveData = new MutableLiveData<>();

        usersDBRef.document(userId).collection(PATH.LIKES_RECEIVED).addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            Set<String> likesReceived = new HashSet<>();
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                Log.i("TAG777", "getLikesReceivedLiveData: " + documentSnapshot.getId());
                likesReceived.add(documentSnapshot.getId());
            }
            likesReceivedLiveData.setValue(likesReceived);
        });
        return likesReceivedLiveData;
    }

    private MutableLiveData<Set<String>> getLikesSentLiveData(String userId) {
        likesSentLiveData = new MutableLiveData<>();

        usersDBRef.document(userId).collection(PATH.LIKES_SENT).addSnapshotListener((value, error) -> {
            if (error != null) {
                return;
            }
            Set<String> likesSent = new HashSet<>();
            for (DocumentSnapshot documentSnapshot : value.getDocuments()) {
                Log.i("TAG777", "getLikesSentLiveData: " + documentSnapshot.getId());
                likesSent.add(documentSnapshot.getId());
            }
            likesSentLiveData.setValue(likesSent);
        });
        return likesSentLiveData;
    }

    private MutableLiveData<Set<String>> getMatchesLiveData(String myUserID) {
        matchesLiveData = new MutableLiveData<>();
        likesSentLiveData.observeForever(likesSent -> {
            likesReceivedLiveData.observeForever(likesReceived -> {
                Set<String> matches = new HashSet<>(likesSent);
                matches.retainAll(likesReceived);
                matchesLiveData.setValue(matches);
            });
        });
        likesReceivedLiveData.observeForever(likesReceived -> {
            likesSentLiveData.observeForever(likesSent -> {
                Set<String> matches = new HashSet<>(likesSent);
                matches.retainAll(likesReceived);
                matchesLiveData.setValue(matches);
            });
        });
        return matchesLiveData;
    }

}
