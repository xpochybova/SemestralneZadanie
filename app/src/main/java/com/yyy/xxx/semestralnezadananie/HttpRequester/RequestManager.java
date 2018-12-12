package com.yyy.xxx.semestralnezadananie.HttpRequester;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yyy.xxx.semestralnezadananie.Activities.LoginActivity;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.LoggedUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestManager extends AsyncTask<String, String, String> {

         List<String> response = new ArrayList<String>();
         private String boundary;
         private static final String LINE_FEED = "\r\n";
         private HttpURLConnection httpConn;
         private String charset = "UTF8";
         private OutputStream outputStream;
         private PrintWriter writer;
         String requestURL;
         String fieldName;
         File uploadFile;

         boolean isVideo;


    FirebaseFirestore databaza;

    public RequestManager(FirebaseFirestore databaza){
        this.databaza = databaza;
    }


    public void makeRequest(String requestURL,String charset,String fieldName, File uploadFile,boolean isVideo) throws IOException {

            this.requestURL = requestURL;
            this.charset = charset;
            this.fieldName = fieldName;
            this.uploadFile = uploadFile;
            this.isVideo = isVideo;
            execute("");
         }

    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        System.out.println("PATH= "+ uploadFile.toPath().toString());

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();
    }

        @Override
        protected String doInBackground(String... uri) {


            boundary = "===" + System.currentTimeMillis() + "===";

            URL url = null;
            try {
                url = new URL(requestURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                httpConn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true); // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
            httpConn.setRequestProperty("Test", "Bonjour");
            try {
                outputStream = httpConn.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                        true);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            try {
                addFilePart(this.fieldName,this.uploadFile);
                finish();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

    public List<String> finish() throws IOException {
        List<String> response = new ArrayList<String>();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }


        try {
            buildUrl(response.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    void buildUrl(String response) throws JSONException {
        String base = "http://mobv.mcomputing.eu/upload/v/";

        //extract uri
        JSONObject json = new JSONObject(response);
        String uri = json.getString("message");

        String videoUrl = base+uri;
        if(isVideo)addVideoToDB(LoggedUser.userId,videoUrl,LoggedUser.userName);
        else addImageToDB(LoggedUser.userId,videoUrl,LoggedUser.userName);
    }

    private void addVideoToDB(String uId, String videourl, String username) {
        String format = "yyyy-MM-dd hh:mm:ss";
        Map<String, Object> newPost = new HashMap<>();
        newPost.put("type", "video");
        newPost.put("imageurl", "");
        newPost.put("videourl", videourl);
        newPost.put("username", username);
        newPost.put("date", FieldValue.serverTimestamp());
        newPost.put("userid", uId);

        databaza.collection("posts").document().set(newPost);
    }
    private void addImageToDB(String uId, String imgurl, String username) {
        String format = "yyyy-MM-dd hh:mm:ss";
        Map<String, Object> newPost = new HashMap<>();
        newPost.put("type", "video");
        newPost.put("imageurl", imgurl);
        newPost.put("videourl", "");
        newPost.put("username", username);
        newPost.put("date", FieldValue.serverTimestamp());
        newPost.put("userid", uId);

        databaza.collection("posts").document().set(newPost);
    }
}

