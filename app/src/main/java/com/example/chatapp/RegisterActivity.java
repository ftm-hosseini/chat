package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;

    Button btn_register;
    MaterialEditText email;
    MaterialEditText password;
    MaterialEditText username;

    DatabaseReference reference;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    register(txt_username, txt_email, txt_password);
                }
            }
        });
    }

    public void register(final String username2, String email2, String password2) {
        auth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userid = auth.getCurrentUser().getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username2);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "You can`t register with this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
