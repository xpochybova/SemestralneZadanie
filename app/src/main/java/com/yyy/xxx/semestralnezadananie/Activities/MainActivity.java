package com.yyy.xxx.semestralnezadananie.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.R;
import com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters.UserRecyclerView_Adapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserRecyclerView_Adapter userAdapter;
    private RecyclerView userRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        List<User> zoznam = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            int cislo = i+1;
            zoznam.add(new User("UserID: "+cislo));
        }

        userAdapter = new UserRecyclerView_Adapter(zoznam);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        userRecyclerView = findViewById(R.id.UsersRecyclerView);
        userRecyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(userRecyclerView);

        userRecyclerView.setAdapter(userAdapter);
    }
}
