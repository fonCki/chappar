package com.example.chappar10.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.chappar10.R;
import com.example.chappar10.ui.view_model.AccessViewModel;
import com.example.chappar10.utils.SetImageTask;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {

    private EditText nickName, email, password, confirmPassword, bio;
    private String nickNameString, passwordString, confirmPasswordString, bioString, photoUrl;
    private CircleImageView profile;
    private Button cancel, save, delete;
    private Uri uri;

    private AccessViewModel accessViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accessViewModel = new ViewModelProvider(this).get(AccessViewModel.class);
        return inflater.inflate(R.layout.fragment_settings, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nickName = view.findViewById(R.id.editTextNickName);
        email = view.findViewById(R.id.editTextEmail);
        password = view.findViewById(R.id.editTextPassword);
        confirmPassword = view.findViewById(R.id.editTextConfirmPassword);
        bio = view.findViewById(R.id.editTextBio);
        profile = view.findViewById(R.id.profile_img);
        cancel = view.findViewById(R.id.cancel_button);
        save = view.findViewById(R.id.update_button);
        delete = view.findViewById(R.id.delete_button);




        accessViewModel.getMyUserLiveData().observe(getViewLifecycleOwner(), user -> {
            Log.i("SettingsFragment", "onViewCreated: " + user.getNickname());
            nickName.setText(user.getNickname());
            email.setText(user.getEmail());
            bio.setText(user.getBio());
            photoUrl = user.getProfileImageUrl();
            Log.i("SettingsFragment", "onViewCreated: " + photoUrl);
            if (photoUrl != null) {
                new SetImageTask(profile).execute(photoUrl);
            } else {
                profile.setImageResource(R.drawable.defaul);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickNameString = nickName.getText().toString();
                passwordString = password.getText().toString();
                confirmPasswordString = confirmPassword.getText().toString();
                bioString = bio.getText().toString();

                if (validateForm()) {
                    accessViewModel.updateUser(nickNameString, passwordString, bioString, uri);
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getContext(), "Error validating data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessViewModel.deleteUser();
                getActivity().onBackPressed();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });
    }
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data.getData() != null) {
                uri = data.getData();
                profile.setImageURI(uri);
            }

        }

        private boolean validateForm() {
            nickNameString = nickName.getText().toString();
            passwordString = password.getText().toString();
            confirmPasswordString = confirmPassword.getText().toString();

            if (!passwordString.isEmpty() || !confirmPasswordString.isEmpty()) {
                if (passwordString.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return false;
                }
                if (!passwordString.equals(confirmPasswordString)) {
                    confirmPassword.setError("Passwords do not match");
                    return false;
                }
            }

            if (nickNameString.isEmpty()) {
                this.nickName.setError("nickname is required");
                this.nickName.requestFocus();
                return false;
            }

            return true;

        }


}