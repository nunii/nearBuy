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

public class userActivity extends AppCompatActivity {

    private Button logout;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        logout = (Button) findViewById(R.id.btn_logout);
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

        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/

    }

    public void logoutBTN(View v){
        if(v.getId() == R.id.btn_logout){
            //handle the click here and make whatever you want
            auth.signOut();
            startActivity(new Intent(userActivity.this, MainActivity.class));
            finish();
        }
    }
   /* public void signOut() {
        auth.signOut();
    }*/
}
