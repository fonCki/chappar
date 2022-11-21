package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.example.chappar10.ui.adapters.UsersAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    RecyclerView userList;
    UsersAdapter adapter;

    DataRepository dataRepository;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        userList = view.findViewById(R.id.user_list);
        userList.hasFixedSize();
        userList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dataRepository = DataRepository.getInstance();

        adapter = new UsersAdapter(getUserList());

        adapter.setOnClickListener(user -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_user_details, bundle);
        });
        userList.setAdapter(adapter);





    }

    private ArrayList<User> getUserList() {
        ArrayList<User> users = new ArrayList<>();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (!user.getUid().equals(currentUser.getUid())) {
                        users.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return users;
    }

    private void move(User user) {
        // move to another fragment

    }
}