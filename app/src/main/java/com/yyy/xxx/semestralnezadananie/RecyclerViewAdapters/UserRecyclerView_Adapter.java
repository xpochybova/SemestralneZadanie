package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.R;

import java.util.List;

public class UserRecyclerView_Adapter extends RecyclerView.Adapter<UserRecyclerView_ViewHolder>{

    private List<Post> recycler_items;
    private List<User> listProfilov;

    public UserRecyclerView_Adapter(List<Post> recycler_items, List<User> listProfilov){
        this.recycler_items = recycler_items;
        this.listProfilov = listProfilov;
    }

    @NonNull
    @Override
    public UserRecyclerView_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row_horizontal, viewGroup, false);

        UserRecyclerView_ViewHolder viewHolder = new UserRecyclerView_ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerView_ViewHolder holder, int i) {
        RecyclerView rc = holder.recyclerView;

        PostRecyclerView_Adapter postAdapter = new PostRecyclerView_Adapter(recycler_items.get(i).getPrispevky(), listProfilov.get(i));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext(),LinearLayoutManager.VERTICAL,false);

        rc.setLayoutManager(linearLayoutManager);

        try {
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(rc);
        }
        catch (Exception e)
        {
            Log.d("SnapHelper","Already Attached");
        }

        rc.setAdapter(postAdapter);
        rc.scrollToPosition(1);
    }

    @Override
    public int getItemCount() {
        return recycler_items.size();
    }
}
