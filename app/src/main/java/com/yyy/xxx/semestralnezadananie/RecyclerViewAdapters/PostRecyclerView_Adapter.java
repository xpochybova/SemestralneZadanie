package com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        else
        {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recyclerview_row_vertical, viewGroup, false);
            return new PostRecyclerView_ViewHolder(itemView);
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
            // TODO: 25.11.2018 setnut view pre prispevoky
            PostRecyclerView_ViewHolder h = (PostRecyclerView_ViewHolder)holder;
            h._post.setText(prvky.get(i).toString());

            videoPlayer = new VideoPlayer(h.playerView);
            videoPlayer.playVideo("",context);

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
            return 1;
        }
    }
}
