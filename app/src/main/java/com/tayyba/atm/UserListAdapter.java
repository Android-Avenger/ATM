package com.tayyba.atm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tayyba.atm.models.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    List<User> userList;
    Context context;

    public UserListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {

        User user = userList.get(position);
        
        holder.mId.setText("ID : "+user.getId());
        holder.mEmail.setText(user.getEmail());
        holder.mPassword.setText("Password :"+user.getPassword());
        holder.mName.setText(user.getName());
        holder.mPin.setText("pin : "+user.getPin());
        holder.mWallet.setText("$ "+user.getWallet());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserListViewHolder extends RecyclerView.ViewHolder {

        TextView mId,mName,mEmail,mPassword,mPin,mWallet;
        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            mId = itemView.findViewById(R.id.userId);
            mName = itemView.findViewById(R.id.userName);
            mEmail = itemView.findViewById(R.id.userEmail);
            mPassword = itemView.findViewById(R.id.userPassword);
            mPin = itemView.findViewById(R.id.userPin);
            mWallet = itemView.findViewById(R.id.userWallet);

        }
    }
}
