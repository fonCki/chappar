package com.example.chappar10.ui.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chappar10.R;
import com.example.chappar10.ui.view_model.AccessViewModel;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private AccessViewModel accessViewModel;
    private Button login;
    private EditText email, password;
    private String emailString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accessViewModel = new ViewModelProvider(this).get(AccessViewModel.class);
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });


        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.loginButton);

        login.setOnClickListener(v -> {
            if (validateFields()) {
                accessViewModel.login(emailString, passwordString).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        accessViewModel.updateLocation();
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean validateFields() {
        emailString = email.getText().toString();
        passwordString = password.getText().toString();

        if (emailString.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            this.email.setError("Email is required and must be valid");
            this.email.requestFocus();
            return false;
        }
        // if password is not valid
        if (passwordString.isEmpty() || passwordString.length() < 6) {
            this.password.setError("Password is required and must be at least 6 characters");
            this.password.requestFocus();
            return false;
        }


        return true;
    }

}