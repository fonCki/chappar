package com.example.chappar10.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.chappar10.R;
import com.example.chappar10.ui.activities.LoginActivity;
import com.example.chappar10.ui.adapters.CardAdapter;
import com.example.chappar10.ui.adapters.UsersAdapter;
import com.example.chappar10.ui.view_model.AccessViewModel;
import com.example.chappar10.ui.view_model.MainFragmentViewModel;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment {

    CardAdapter adapter;
    MainViewModel viewModel;


        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);


        }

        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
            CardStackLayoutManager manager = new CardStackLayoutManager(getContext());
            manager.setStackFrom(StackFrom.Top);
            cardStackView.setLayoutManager(manager);

            viewModel = new ViewModelProvider(this).get(MainViewModel.class);
            adapter = new CardAdapter(new ArrayList<>());

           cardStackView.setAdapter(adapter);

            viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
                adapter.setUsers(users);
            });
            
            //set the listener for the card
//
//            CardStackListener listener = new CardStackListener() {
//                @Override
//                public void onCardDragging(Direction direction, float ratio) {
//
//                }
//
//                @Override
//                public void onCardSwiped(Direction direction) {
//                    if (direction == Direction.Right) {
//                        Log.d("TAG123", "onCardSwiped: " + adapter.getItem(manager.getTopPosition() - 1).getName());
//                    }
//                    if (direction == Direction.Left) {
//                        Log.d("TAG123", "onCardSwiped: " + adapter.getItem(manager.getTopPosition() - 1).getName());
//                    }
//                }
//
//                @Override
//                public void onCardRewound() {
//
//                }
//
//                @Override
//                public void onCardCanceled() {
//
//                }
//
//                @Override
//                public void onCardAppeared(View view, int position) {
//
//                }
//
//                @Override
//                public void onCardDisappeared(View view, int position) {
//
//                }
//            };

            //set the listener to the card stack view

        }

}