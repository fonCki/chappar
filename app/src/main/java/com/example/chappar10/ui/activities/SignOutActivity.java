package com.example.chappar10.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.chappar10.R;
import com.example.chappar10.ui.view_model.AccessViewModel;

public class SignOutActivity extends AppCompatActivity {
    private AccessViewModel accessViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        accessViewModel = new ViewModelProvider(this).get(AccessViewModel.class);
        accessViewModel.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}