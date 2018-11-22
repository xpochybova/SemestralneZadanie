package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yyy.xxx.semestralnezadananie.R;

public class UserRecyclerView_ViewHolder extends RecyclerView.ViewHolder {
    public TextView _user;
    public RecyclerView recyclerView;

    public UserRecyclerView_ViewHolder(@NonNull View itemView) {
        super(itemView);

        recyclerView = itemView.findViewById(R.id.PostsRecyclerView);

    }
}
