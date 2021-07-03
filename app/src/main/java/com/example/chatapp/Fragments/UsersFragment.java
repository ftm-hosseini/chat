package com.example.chatapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatapp.Adapter.UserAdapter;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    public List<User> mUsers;
    public RecyclerView recyclerView;
    public EditText search_users;
    public UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList();
        ReadUsers();

/*        search_users = view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UsersFragment.this.searchUsers(s.toString().toLowerCase());
            }

            public void afterTextChanged(Editable s) {
            }
        });*/
        return view;
    }

/*    public void searchUsers(String s) {
        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query startAt = FirebaseDatabase.getInstance().getReference("Users").orderByChild(Event.SEARCH).startAt(s);
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb.append("");
        startAt.endAt(sb.toString()).addValueEventListener(new ValueEventListener() {
            static final *//* synthetic *//* boolean $assertionsDisabled = false;

            static {
                Class<UsersFragment> cls = UsersFragment.class;
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
                UsersFragment.this.mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = (User) snapshot.getValue(User.class);
                    if (!user.getId().equals(fuser.getUid())) {
                        UsersFragment.this.mUsers.add(user);
                    }
                }
                UsersFragment usersFragment = UsersFragment.this;
                usersFragment.userAdapter = new UserAdapter(usersFragment.getContext(), UsersFragment.this.mUsers, false);
                UsersFragment.this.recyclerView.setAdapter(UsersFragment.this.userAdapter);
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }*/

    private void ReadUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        assert user != null;
                        assert firebaseUser != null;
                        if (!user.getId().equals(firebaseUser.getUid())) {
                            mUsers.add(user);
                        }
                    }

                    userAdapter = new UserAdapter(getContext(), mUsers, false);
                    recyclerView.setAdapter(userAdapter);
                }
            //}

            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
