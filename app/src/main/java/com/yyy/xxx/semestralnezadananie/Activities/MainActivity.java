package com.yyy.xxx.semestralnezadananie.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.R;
import com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters.UserRecyclerView_Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserRecyclerView_Adapter userAdapter;
    private RecyclerView userRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseFirestore databaza;

    List<Post> zoznamPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoznamPosts = new ArrayList<>();
        databaza = FirebaseFirestore.getInstance();
        readFromDB();

        List<User> zoznam = new ArrayList<>();
        for (int i = 10; i < 15; i++){
            int cislo = i+1;
            zoznam.add(new User("UserID: "+cislo));
        }

        // TODO pouzit zoznamPosts namiesto List<User> zoznam
        userAdapter = new UserRecyclerView_Adapter(zoznam);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        userRecyclerView = findViewById(R.id.UsersRecyclerView);
        userRecyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(userRecyclerView);

        userRecyclerView.setAdapter(userAdapter);
    }

    private void readFromDB(){
        databaza.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(" date", document.getId() + " => " + document.getData().get("date"));
                                Log.d(" USERNAME",  "  " + document.getData().get("username"));
                                Log.d(" imageurl",  " " + document.getData().get("imageurl"));
                                Log.d(" type",  " " + document.getData().get("type"));
                                Log.d(" videourl", " " + document.getData().get("videourl"));

                                zoznamPosts.add(new Post( document.getId(),
                                        document.getData().get("type").toString(),
                                        document.getData().get("videourl").toString(),
                                        document.getData().get("imageurl").toString(),
                                        document.getData().get("username").toString(),
                                        document.getData().get("date").toString(),
                                        document.getData().get("userid").toString()
                                ));
                            }
                        } else {
                            Log.w("ERR", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
