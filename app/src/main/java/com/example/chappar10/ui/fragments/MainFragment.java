package com.example.chappar10.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chappar10.R;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.adapters.CardAdapter;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment implements CardStackListener {

    MainViewModel mainViewModel;
    CardAdapter adapter;
    CardStackLayoutManager manager;
    CardStackView cardStackView;


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //set the variables
            cardStackView = view.findViewById(R.id.card_stack_view);
            manager = new CardStackLayoutManager(getContext(), this);
            mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
            CircleImageView like = view.findViewById(R.id.fab_like);
            CircleImageView dislike = view.findViewById(R.id.fab_dislike);

            dislike.setOnClickListener(v->{
                cardStackView.swipe();
            });

            like.setOnClickListener(v->{
            });


            //set the adapter
            manager.setStackFrom(StackFrom.Top);
            cardStackView.setLayoutManager(manager);
            adapter = new CardAdapter(new ArrayList<>());
            cardStackView.setAdapter(adapter);


            mainViewModel.getViginUsers().observe(getViewLifecycleOwner(), users -> {
                List<User> list = new ArrayList<>();
                users.forEach(user -> {
                    if (!user.getUid().equals(mainViewModel.getMyUserID())) {
                        list.add(user);
                    }
                });
                adapter.setUsers(list);
            });

        }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        if (direction == Direction.Right) {
            mainViewModel.like(adapter.getItem(manager.getTopPosition() - 1).getUid());
        } else if (direction == Direction.Left) {
            mainViewModel.dislike(adapter.getItem(manager.getTopPosition() - 1).getUid());
        }
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}