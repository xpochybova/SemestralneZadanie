package com.yyy.xxx.semestralnezadananie.Entities;

public class Post
{
    private String title;
    private String username;

    public Post(String title, String username)
    {
        this.username = username;
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title + " " + this.username;
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
}
