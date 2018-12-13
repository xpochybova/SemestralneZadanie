package com.yyy.xxx.semestralnezadananie.Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Post implements Serializable, Cloneable
{
    private String id;
    private String type;
    private String videourl;
    private String imageurl;
    private String username;
    private String date;
    private String userid;

    private ArrayList<Post> prispevkyPouzivatela;

    private String title;

    public Post(String title, String username)
    {
        this.username = username;
        this.title = title;
    }

    public Post(String id, String type, String videourl, String imageurl, String username, String date, String userid) {
        this.id = id;
        this.type = type;
        this.videourl = videourl;
        this.imageurl = imageurl;
        this.username = username;
        this.date = date;
        this.userid = userid;
    }

    public Post(String id, String type, String videourl, String imageurl, String username, String date) {
        this.id = id;
        this.type = type;
        this.videourl = videourl;
        this.imageurl = imageurl;
        this.username = username;
        this.date = date;
    }

    public static Post copy( Post post ) {
        Post newPost = new Post(post.id,post.type, post.videourl,post.imageurl,post.username, post.date);
        return newPost;
    }

    @Override
    public String toString() {
        return this.username + " " + this.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getType() {return type; }

    public void setType(String type) { this.type = type; }

    public String getVideourl() { return videourl; }

    public void setVideourl(String videourl) {  this.videourl = videourl; }

    public String getImageurl() { return imageurl; }

    public void setImageurl(String imageurl) { this.imageurl = imageurl; }

    public String getDate() { return date.replace("GMT+01:00", "").substring(4,25); }

    public void setDate(String date) { this.date = date; }

    public String getUserid() { return userid; }

    public void setUserid(String userid) { this.userid = userid; }

    public ArrayList<Post> getPrispevky() { return prispevkyPouzivatela; }

    public void setPrispevky(ArrayList<Post> p) { this.prispevkyPouzivatela = p; }

}
