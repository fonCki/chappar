package com.example.chappar10.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.chappar10.R;
import com.example.chappar10.data.DataRepository;
import com.example.chappar10.data.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText name, email, password;
    private Switch aSwitch;
    CircleImageView profile;

    Button register;

    FirebaseStorage storage;
    FirebaseDatabase database;

    boolean profileSelected = false;

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();



        register = findViewById(R.id.register);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        name = findViewById(R.id.nickname);
        aSwitch = findViewById(R.id.gender_switch);

        profile = findViewById(R.id.profile_img);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (profileSelected == false) {
                    if (isChecked) {
                        profile.setImageResource(R.drawable.angelina);
                    } else {
                        profile.setImageResource(R.drawable.brad_pitt);
                    }
                }

            }
        });


        register.setOnClickListener(v -> {
            registerUser();

        });



    }


    private void registerUser() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();
        String nickName = this.name.getText().toString();

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

        if (nickName.isEmpty()) {
            this.name.setError("nickname is required");
            this.name.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            DataRepository.getInstance().addUser(mAuth.getCurrentUser().getUid(), new User(nickName, email));
            StorageReference reference = storage.getReference().child("profile_pics").child(mAuth.getCurrentUser().getUid()).child("profile.jpg");
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(SignUp.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                }
            }   );
            mAuth.signOut();
            startActivity(new Intent(this, Login.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            profileSelected = true;
            uri = data.getData();
            profile.setImageURI(uri);
            Log.i("#TAG2", "onSuccess: " + uri);

        }


    }
}