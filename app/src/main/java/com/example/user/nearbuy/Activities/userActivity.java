package com.example.user.nearbuy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.user.nearbuy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class userActivity extends AppCompatActivity {

    private Button logout, makeList;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //FirebaseMessaging.getInstance().subscribeToTopic("nearBuy");

        logout = (Button) findViewById(R.id.btn_logoutManager);
        makeList = (Button) findViewById(R.id.button_makeListManager);
        auth = FirebaseAuth.getInstance();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(userActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    public void logoutBTN(View v){
        if(v.getId() == R.id.btn_logoutManager){
            //handle the click here and make whatever you want
            auth.signOut();
            startActivity(new Intent(userActivity.this, MainActivity.class));
            finish();
        }
    }

    public void makeListBTN(View v){
        if(v.getId() == R.id.button_makeListManager){
            startActivity(new Intent(userActivity.this, myListActivity.class));
        }
    }
  }


