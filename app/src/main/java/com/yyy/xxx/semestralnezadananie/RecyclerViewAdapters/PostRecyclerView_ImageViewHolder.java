package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyy.xxx.semestralnezadananie.R;

public class PostRecyclerView_ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView imageText;
   // public ImageView image;

    public PostRecyclerView_ImageViewHolder(@NonNull View itemView) {
        super(itemView);

        imageText = itemView.findViewById(R.id.textViewImage);
      //  image = itemView.findViewById(R.id.my_image_view);

    }
}
