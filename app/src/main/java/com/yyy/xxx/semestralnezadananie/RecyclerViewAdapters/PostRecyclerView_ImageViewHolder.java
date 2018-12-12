package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.yyy.xxx.semestralnezadananie.R;


public class PostRecyclerView_ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView imageText;
    public TextView imageDate;
    public ImageView imageView;
    public RequestManager glideImageReq;

    public PostRecyclerView_ImageViewHolder(@NonNull View itemView) {
        super(itemView);

        imageText = itemView.findViewById(R.id.textViewImage);
        imageDate = itemView.findViewById(R.id.textViewImageDate);

        imageView = itemView.findViewById(R.id.my_image_view);
        glideImageReq = Glide.with(itemView);

    }

}
