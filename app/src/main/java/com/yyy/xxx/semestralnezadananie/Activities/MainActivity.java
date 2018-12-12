package com.yyy.xxx.semestralnezadananie.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yyy.xxx.semestralnezadananie.Entities.Post;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.FilePathHelper;
import com.yyy.xxx.semestralnezadananie.HttpRequester.MultipartUtility;
import com.yyy.xxx.semestralnezadananie.HttpRequester.RequestManager;
import com.yyy.xxx.semestralnezadananie.R;
import com.yyy.xxx.semestralnezadananie.RecyclerViewAdapters.UserRecyclerView_Adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserRecyclerView_Adapter userAdapter;
    private RecyclerView userRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseFirestore databaza;

    List<Post> zoznamPosts;
    List<User> zoznamUsers;

    /******************* NEW POST ******************************/
    public static Dialog newPostDialog;
    public static TextView filename_textfield;
    public static Button upload_button;

    public static RadioButton video;
    public static RadioButton img;

    public static String upload_filepath;

    public static final int PICK_IMAGE = 1;

    /******************* NEW POST END ******************************/

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

    public void ShowNewPostDialog(View view)
    {
        newPostDialog = new Dialog(this);

        newPostDialog.setContentView(R.layout.new_post_dialog);

        newPostDialog.setCancelable(false);

        Button cancel_button = newPostDialog.findViewById(R.id.button_cancel);
        Button select_file_button = newPostDialog.findViewById(R.id.select_file_button);

        video = newPostDialog.findViewById(R.id.radioButtonVideo);
        img = newPostDialog.findViewById(R.id.radioButtonImage);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.newPostDialog.cancel();
            }
        });

        upload_button = newPostDialog.findViewById(R.id.button_upload);
        upload_button.setEnabled(false);

        filename_textfield = newPostDialog.findViewById(R.id.textView_filename);

        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload(v);
            }
        });

        select_file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean isVideo = video.isChecked();
                boolean isImage = img.isChecked();

                Intent intent;
                if (isImage)
                {
                    String[] mimeTypes = {"image/jpeg", "image/png"};
                    intent = new Intent(Intent.ACTION_GET_CONTENT)
                            .setType("image/*")
                            .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }
                else
                {
                    String[] mimeTypes = {"video/mp4"};
                    intent = new Intent(Intent.ACTION_GET_CONTENT)
                            .setType("video/*")
                            .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                }


                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });



        newPostDialog.show();
    }

    private void Upload(View v)
    {
        boolean isVideo = video.isChecked();
        boolean isImage = img.isChecked();

        String httpURL =  "http://mobv.mcomputing.eu/upload/index.php";

        try {

            RequestManager rm = new RequestManager(databaza);
            rm.makeRequest(httpURL,"UTF8","upfile",new File(upload_filepath),isVideo);

        } catch (IOException e) {
            e.printStackTrace();
        }
     /* if(isImage){}else{}*/
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data == null)
            return;

        if (requestCode == PICK_IMAGE) {
            try
            {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant that should be quite unique

                    return;
                }

                String path = null;
                if (Build.VERSION.SDK_INT < 11)
                    path = FilePathHelper.getRealPathFromURI_BelowAPI11(MainActivity.this, data.getData(), video.isChecked());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    path = FilePathHelper.getRealPathFromURI_API11to18(MainActivity.this, data.getData(), video.isChecked());

                    // SDK > 19 (Android 4.4)
                else
                    path = FilePathHelper.getRealPathFromURI_API19(MainActivity.this, data.getData(), video.isChecked());


                File file = new File(path);
                double file_sizeKB = file.length()/1024;
                double file_sizeMB = file_sizeKB/1024;

                if (file_sizeMB < 8)
                {
                    upload_button.setEnabled(true);
                    filename_textfield.setError(null);
                    filename_textfield.setText("SIZE OF FILE: " + file_sizeMB + " Mb");
                }
                else
                {
                    upload_button.setEnabled(false);
                    filename_textfield.setError("SIZE OF FILE MUST BE < 8 Mb");
                    filename_textfield.setText("SIZE OF FILE: " + file_sizeMB + " Mb, MUST BE < 8 Mb");
                }


                // TODO: 8. 12. 2018 SPRACOVAT OBRAZOK/VIDEO ABY SA DAL POSLAT NA SERVER

                upload_filepath = path;


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void changeType(View v)
    {
        upload_button.setEnabled(false);
    }

    public void logOut(View view)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();
        
        this.startActivity(new Intent(this,LoginActivity.class));
    }
    
    public void refresh(View v)
    {
        new ReadFromDatabase(this, new Intent(this, MainActivity.class), FirebaseFirestore.getInstance());
    }
}
