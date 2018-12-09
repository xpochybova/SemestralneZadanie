package com.yyy.xxx.semestralnezadananie.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.Entities.User;


public class ReadFromDatabase {

    private Context context;
    private FirebaseFirestore databaza;
    private Intent intent;

    public ArrayList<Post> listPosts;
    public ArrayList<User> listUsers;
    public Map<String, ArrayList<Post>> mapAllToUserId;


    public ReadFromDatabase(Context ctx,Intent intent, FirebaseFirestore databaza) {
        this.intent = intent;
        this.context = ctx;
        this.databaza = databaza;
        listPosts = new ArrayList<Post>();
        listUsers = new ArrayList<User>();
        mapAllToUserId = new HashMap<>();

        readUsersFromDB();
    }


    private void readUsersFromDB(){
        databaza.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User u = new User( document.getId(),
                                        document.getData().get("username").toString(),
                                        document.getData().get("date").toString(),
                                        (Integer.parseInt(document.getData().get("numberOfPosts").toString()))
                                );
                                listUsers.add(u);
                            }
                        } else {
                            Log.w("ERR", "Error getting documents.", task.getException());
                        }

                        readPostsFromDB();

                    }
                });
    }


    private void readPostsFromDB(){
        databaza.collection("posts")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                String userid =  document.getData().get("userid").toString();

                                Post p = new Post( document.getId(),
                                        document.getData().get("type").toString(),
                                        document.getData().get("videourl").toString(),
                                        document.getData().get("imageurl").toString(),
                                        document.getData().get("username").toString(),
                                        document.getData().get("date").toString(),
                                        document.getData().get("userid").toString()
                                );

                                listPosts.add(p);

                                if(mapAllToUserId.get(userid) == null){
                                    ArrayList<Post> newList = new ArrayList<>();
                                    newList.add(p);

                                    mapAllToUserId.put(document.getData().get("userid").toString(),newList);
                                } else {
                                    mapAllToUserId.get(userid).add(p);
                                }
                            }
                        } else {
                            Log.w("ERR", "Error getting documents.", task.getException());
                        }

                        for (Post l : listPosts){
                            ArrayList<Post> newlist = new ArrayList<>();
                            //  list.add(l); //TODO PRIDAT PRVY POST NA ZACIATOK LISTU !

                            newlist.addAll(mapAllToUserId.get(l.getUserid()));
                            l.setPrispevky(newlist);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ExtraPosts", listPosts);
                         //  bundle.putSerializable("ExtraPosts", listUsers);
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }
                });
    }


}
