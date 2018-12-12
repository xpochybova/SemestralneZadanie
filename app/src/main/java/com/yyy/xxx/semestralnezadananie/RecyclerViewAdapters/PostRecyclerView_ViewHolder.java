package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.yyy.xxx.semestralnezadananie.R;

public class PostRecyclerView_ViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView postDate;

    PlayerView playerView;


    public PostRecyclerView_ViewHolder(@NonNull View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.userName);
        postDate = itemView.findViewById(R.id.postDate);

        playerView = itemView.findViewById(R.id.playerView);

    }
}
