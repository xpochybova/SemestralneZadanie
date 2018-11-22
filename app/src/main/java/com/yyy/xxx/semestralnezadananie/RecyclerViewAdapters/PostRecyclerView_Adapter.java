package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.R;

import java.util.List;

public class PostRecyclerView_Adapter extends RecyclerView.Adapter<PostRecyclerView_ViewHolder>
{
    private List<Post> prvky;

    public PostRecyclerView_Adapter(List<Post> prvky){
        this.prvky = prvky;
    }

    @NonNull
    @Override
    public PostRecyclerView_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row_vertical, viewGroup, false);

        PostRecyclerView_ViewHolder viewHolder = new PostRecyclerView_ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerView_ViewHolder holder, int i) {
        holder._post.setText(prvky.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return prvky.size();
    }
}
