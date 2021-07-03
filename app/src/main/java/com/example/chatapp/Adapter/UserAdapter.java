package com.example.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.chatapp.MessageActivity;
import com.example.chatapp.Model.User;
import com.example.chatapp.R;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private boolean ischat;

    public Context mContext;
    private List<User> mUsers;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img_off;

        public ImageView img_on;
        public ImageView profile_image;
        public TextView username;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.image_on);
            img_off = itemView.findViewById(R.id.image_off);
        }
    }

    public UserAdapter(Context mContext2, List<User> mUsers2, boolean ischat2) {
        mUsers = mUsers2;
        mContext = mContext2;
        ischat = ischat2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user = mUsers.get(position);
        holder.username.setText(user.getUsername());

        if (user.getImageURL().equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.profile_image);
        } else {
            Glide.with(this.mContext).load(user.getImageURL()).into(holder.profile_image);
        }
        if (!this.ischat) {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        } else if (user.getStatus().equals("online")) {
            holder.img_on.setVisibility(View.VISIBLE);
            holder.img_off.setVisibility(View.GONE);
        } else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserAdapter.this.mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                UserAdapter.this.mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
