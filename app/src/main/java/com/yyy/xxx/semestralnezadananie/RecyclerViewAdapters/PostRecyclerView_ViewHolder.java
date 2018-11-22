package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yyy.xxx.semestralnezadananie.R;

public class PostRecyclerView_ViewHolder extends RecyclerView.ViewHolder {
    public TextView _post;

    public PostRecyclerView_ViewHolder(@NonNull View itemView) {
        super(itemView);

        _post = itemView.findViewById(R.id.recyclerview_post_vertical);

    }
}
