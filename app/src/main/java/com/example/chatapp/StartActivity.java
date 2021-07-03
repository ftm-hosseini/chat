package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    FirebaseUser firebaseuser;

    Button login;
    Button register;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
    }

    public void onStart() {
        super.onStart();
        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseuser != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }
}
