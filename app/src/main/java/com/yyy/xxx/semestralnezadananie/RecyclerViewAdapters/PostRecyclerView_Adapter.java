package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.R;
import com.yyy.xxx.semestralnezadananie.VideoPlayer.VideoPlayer;

import java.util.List;

public class PostRecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<Post> prvky;
    private User profil;

    private VideoPlayer videoPlayer;

    private Context context;

    public PostRecyclerView_Adapter(List<Post> prvky, User profil){
        this.prvky = prvky;
        this.profil = profil;



        // VYTVORI PLACEHOLDER PRE PROFIL NA NULTEJ POZICII
        if (prvky.get(0) != null)
            this.prvky.add(0,null);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        context = viewGroup.getContext();

        if (i == 0)
        {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.user_profile, viewGroup, false);
            return new UserProfileViewHolder(itemView);
        }
        else if( i == 1)
        {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_row_vertical, viewGroup, false);
            return new PostRecyclerView_ViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.image_post_layout, viewGroup, false);
            return new PostRecyclerView_ImageViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i)
    {
        if (i == 0) // USER PROFILE
        {
            // TODO: 25.11.2018 setnut view pre pouzivatela
            UserProfileViewHolder h = (UserProfileViewHolder)holder;
            h.username.setText(profil.getUsername());
            h.dateRegistered.setText(profil.getDate());
            h.postsCount.setText(profil.getNumberOfPosts().toString());
        }
        else // PRISPEVOK
        {
            if(prvky.get(i).getType().equals("video")) {

                PostRecyclerView_ViewHolder h = (PostRecyclerView_ViewHolder) holder;
                h.userName.setText(prvky.get(i).getUsername());
                h.postDate.setText(prvky.get(i).getDate());

                  videoPlayer = new VideoPlayer(h.playerView);
                  videoPlayer.playVideo(prvky.get(i).getVideourl(),context);

            }

            if(prvky.get(i).getType().equals("image")) {
                PostRecyclerView_ImageViewHolder h = (PostRecyclerView_ImageViewHolder) holder;
                h.imageText.setText(prvky.get(i).getUsername());
                h.imageDate.setText( prvky.get(i).getDate());
                h.glideImageReq.load(prvky.get(i).getImageurl()).into(h.imageView);
            }

        }
    }

    @Override
    public int getItemCount() {
        return prvky.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        if (position == 0) // KED NULTA POZICIA, ZOBRAZ PROFIL LAYOUT
        {
            return 0;
        }
        else              // INAK ZOBRAZ POST LAYOUT
        {
            if(prvky.get(position).getType().equals("video"))
                 return 1;
            else return 2;
        }
    }
}
