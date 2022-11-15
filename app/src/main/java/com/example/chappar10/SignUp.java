package com.example.chappar10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText name, lastname, email, password;
    ProgressBar progressBar;
    Button register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        name = findViewById(R.id.editTextName);
        lastname = findViewById(R.id.editTextLastName);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);




        register.setOnClickListener(v -> {
            registerUser();

        });



    }


    private void registerUser() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String name = this.name.getText().toString();
        String lastname = this.lastname.getText().toString();

        if (email.isEmpty()) {
            this.email.setError("Email is required");
            this.email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            this.password.setError("Password is required");
            this.password.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            this.name.setError("Name is required");
            this.name.requestFocus();
            return;
        }

        if (lastname.isEmpty()) {
            this.lastname.setError("Lastname is required");
            this.lastname.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            DataRepository.getInstance().addUser(mAuth.getCurrentUser().getUid(), new User(name, email));
            mAuth.signOut();
            startActivity(new Intent(this, Login.class));
        });
    }
}