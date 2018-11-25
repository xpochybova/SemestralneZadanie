package com.yyy.xxx.semestralnezadananie.Entities;

import java.util.ArrayList;
import java.util.List;

public class User
{
    private String id;
    private String username;
    private String date;
    private Integer numberOfPosts;
    private List<Post> prispevky;


    public User(String id)
    {
        this.id = id;
        List<Post> zoznam = new ArrayList<Post>();
        for (int i = 0; i < 10; i++){
            int cislo = i+1;
            zoznam.add(new Post("Prispevok: "+cislo, id));
        }

        prispevky = zoznam;
    }

    public User(String username, String date, Integer numberOfPosts) {
        this.username = username;
        this.date = date;
        this.numberOfPosts = numberOfPosts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Post> getPrispevky() {
        return prispevky;
    }

    public void setPrispevky(List<Post> prispevky) {
        this.prispevky = prispevky;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }
}
