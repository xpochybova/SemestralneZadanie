package com.yyy.xxx.semestralnezadananie.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.yyy.xxx.semestralnezadananie.Entities.User;
import com.yyy.xxx.semestralnezadananie.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private FirebaseAuth mAuth;

    private EditText passwd_editText;
    private EditText email_editText;
    private EditText username_editText;

    private TextView passwd_textView;
    private TextView email_textView;
    private TextView username_textView;
    private String newUsername;

    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_editText = findViewById(R.id.editText_email);
        passwd_editText = findViewById(R.id.editText_passwd);
        username_editText = findViewById(R.id.editText_username);

        email_textView = findViewById(R.id.textView_email);
        passwd_textView = findViewById(R.id.textView_passwd);
        username_textView = findViewById(R.id.textView_username);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createUser(String email, String password)
    {
        if (!validateForm(true))
        {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void singinUser(String email, String password)
    {
        if (!validateForm(false))
        {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            toMainActivity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void getUserInfo()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            String uid = user.getUid();
        }
    }

    private void updateUI(FirebaseUser user)
    {
        if (user != null)
            Toast.makeText(LoginActivity.this, user.getEmail(),
                Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(LoginActivity.this, "Singed out",
                    Toast.LENGTH_SHORT).show();
    }

    private boolean validateForm(boolean isCreate)
    {

        boolean valid = true;

        if (isCreate)
        {
            username_editText.setVisibility(View.VISIBLE);
            username_textView.setVisibility(View.VISIBLE);

            String username = username_editText.getText().toString();
            this.newUsername = username;

            if (TextUtils.isEmpty(username))
            {
                username_editText.setError("Required.");

                valid = false;

            } else {
                username_editText.setError(null);
            }

        } else {
            username_editText.setText("");
            username_editText.setVisibility(View.GONE);
            username_textView.setVisibility(View.GONE);
        }

        String email = email_editText.getText().toString(); // ^.+[@].+[.].+$

        if (TextUtils.isEmpty(email))
        {

            email_editText.setError("Required.");

            valid = false;

        } else {

            email_textView.setError(null);

        }

        String password = passwd_editText.getText().toString();

        if (isCreate && !isValidPassword(password))
        {
            passwd_editText.setError("must contain upper case, lower case character and number, must contain at least 6 characters.");
            valid = false;

        } else {
            passwd_textView.setError(null);
        }

        return valid;
    }

    private boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).{6,40}";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public void createAccountOnClick(View view)
    {
        createUser(this.email_editText.getText().toString(), this.passwd_editText.getText().toString());
    }

    public void singInOnClick(View view)
    {
        singinUser(this.email_editText.getText().toString(), this.passwd_editText.getText().toString());
    }


    public void toMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addToDatabase( FirebaseUser userF ){

        if (userF != null) {
            Time now = new Time();
            now.setToNow();
            // String name = userF.getDisplayName();
            String uid = userF.getUid();

            //   String id = databaseUsers.push().getKey();

            User user = new User(this.newUsername, now.toString(), 0);
            databaseUsers.child(uid).setValue(user);


            Toast.makeText(LoginActivity.this, "Added user to database",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(LoginActivity.this, "Added user FAIL",
                    Toast.LENGTH_SHORT).show();
        }

    }
}
