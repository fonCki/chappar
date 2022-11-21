package com.example.chappar10.ui.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.chappar10.R;
import com.example.chappar10.ui.view_model.AccessViewModel;

public class LoginActivity extends AppCompatActivity {
    private AccessViewModel accessViewModel;
    private Button login;
    private EditText email, password;

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
            accessViewModel.login(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    accessViewModel.updateLocation();
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

}