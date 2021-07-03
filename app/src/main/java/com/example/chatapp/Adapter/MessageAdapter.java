package com.example.chatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.chatapp.Model.Chat;
import com.example.chatapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    FirebaseUser fuser;
    private String imageUrl;
    private List<Chat> mChat;
    private Context mContext;



    public MessageAdapter(Context mContext2, List<Chat> mChat2, String imageUrl2) {

        mChat = mChat2;
        mContext = mContext2;
        imageUrl = imageUrl2;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());

        if (imageUrl.equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.profile_image);
        } else {
            Glide.with(mContext).load(this.imageUrl).into(holder.profile_image);
        }
/*        if (position != mChat.size() - 1) {
            holder.text_seen.setVisibility(View.GONE);
        } else if (chat.isIsseen()) {
            holder.text_seen.setText("seen");
        } else {
            holder.text_seen.setText("delivered");
        }*/
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public int getItemViewType(int position) {

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if ((mChat.get(position)).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profile_image;
        public TextView show_message;
        //public TextView text_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            //text_seen = itemView.findViewById(R.id.text_seen);
        }
    }
}
