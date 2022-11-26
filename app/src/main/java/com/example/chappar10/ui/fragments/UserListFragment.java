package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chappar10.R;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.adapters.UsersAdapter;
import com.example.chappar10.ui.view_model.MainViewModel;
import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    MainViewModel mainViewModel;
    RecyclerView userList;
    UsersAdapter adapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userList = view.findViewById(R.id.user_list);
        userList.hasFixedSize();
        userList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        adapter = new UsersAdapter(new ArrayList<>());

        adapter.setOnClickListener(user -> {
            //send user to next fragment
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.nav_user_details, bundle);
        });
        userList.setAdapter(adapter);

        List<User> list = new ArrayList<>();
        mainViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            list.clear();
            for (User user : users) {
                if (!user.getUid().equals(mainViewModel.getMyUserID())) {
                    list.add(user);
                }
            }
            adapter.setUsers(list);
        });

        mainViewModel.getUser(mainViewModel.getMyUserID()).observe(getViewLifecycleOwner(), user -> {
            list.remove(user);
            adapter.setUsers(list);
        });
    }

}