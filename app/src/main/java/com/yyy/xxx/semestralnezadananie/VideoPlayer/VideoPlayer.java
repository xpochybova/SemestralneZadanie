package com.yyy.xxx.semestralnezadananie.VideoPlayer;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayer {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    public boolean isDisabled = false;

    public VideoPlayer(View playerView){
        this.playerView = (PlayerView) playerView;
    }

    public void playVideo(String url,Context context){

        if(playerView == null || isDisabled)
            return;


        player = ExoPlayerFactory.newSimpleInstance(context,new DefaultTrackSelector());
        playerView.setPlayer(player);

       // Uri videoUri = Uri.parse(url);
        Uri videoUri = Uri.parse(Environment.getExternalStorageDirectory()+"/Movies/Best Cry Ever.mp4");

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context,"exo_demo"));
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);


        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }


}
