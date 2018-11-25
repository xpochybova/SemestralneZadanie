package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yyy.xxx.semestralnezadananie.R;

public class UserProfileViewHolder extends RecyclerView.ViewHolder
{
    public TextView username;
    public TextView dateRegistered;
    public TextView postsCount;


    public UserProfileViewHolder(@NonNull View itemView)
    {
        super(itemView);

        username = itemView.findViewById(R.id.username_text);
        dateRegistered = itemView.findViewById(R.id.date_text);
        postsCount = itemView.findViewById(R.id.posts_text);
    }
}
