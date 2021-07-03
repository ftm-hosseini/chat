package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.chatapp.Adapter.MessageAdapter;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    ImageButton btn_send;
    FirebaseUser firebaseUser;
    Intent intent;
    List<Chat> mChat;

    MessageAdapter messageAdapter;

    CircleImageView profile_image;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ValueEventListener seenListener;
    EditText txt_send;
    TextView username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        txt_send = findViewById(R.id.txt_send);
        intent = getIntent();

        final String userid = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.profile_image);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }
                readMessages(firebaseUser.getUid(), userid, user.getImageURL());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        btn_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = txt_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "You can`t send empty message", Toast.LENGTH_SHORT).show();
                }
                txt_send.setText("");
            }
        });
    }

    public void sendMessage(String sender, String receiver, String message) {

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        //hashMap.put("isseen", Boolean.valueOf(false));

        reference2.child("Chats").push().setValue(hashMap);
    }

    public void readMessages(final String myid, final String userid, final String imageUrl) {
        mChat = new ArrayList();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myid) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(myid)) {
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
        //seenMessage(userid);
    }

/*    private void seenMessage(final String userId) {
        this.reference = FirebaseDatabase.getInstance().getReference("Chats");
        this.seenListener = this.reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = (Chat) snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(MessageActivity.this.firebaseUser.getUid()) && chat.getSender().equals(userId)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", Boolean.valueOf(true));
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/

    public void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    public void onResume() {
        super.onResume();
        status("online");
    }

    public void onPause() {
        super.onPause();
        status("offline");
    }
}
