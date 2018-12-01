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
    List<User> zoznamUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zoznamPosts = new ArrayList<>();
        zoznamUsers = new ArrayList<>();

        ArrayList<User> list1 = (ArrayList<User>) getIntent().getSerializableExtra("ExtraUsers");
        ArrayList<Post> list2 = (ArrayList<Post>) getIntent().getSerializableExtra("ExtraPosts");

        Log.d(" list1", " " + list1.size());
        Log.d(" list2", " " + list2.size());

        databaza = FirebaseFirestore.getInstance();

        userAdapter = new UserRecyclerView_Adapter(list2,list1);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        userRecyclerView = findViewById(R.id.UsersRecyclerView);
        userRecyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(userRecyclerView);

        userRecyclerView.setAdapter(userAdapter);
    }


}
