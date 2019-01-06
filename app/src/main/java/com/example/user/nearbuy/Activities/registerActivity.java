package com.example.user.nearbuy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.nearbuy.Classes.Customer;
import com.example.user.nearbuy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class registerActivity extends AppCompatActivity  {

    private EditText inputEmail, inputPassword, inputName;
    private Button btn_register;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private Customer customer;
    private static int idNum = 1;
    private DatabaseReference mDatabase, newUser;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        inputEmail = findViewById(R.id.text_email);
        inputPassword = findViewById(R.id.text_pw);
        inputName = findViewById(R.id.text_nickname);
        btn_register = findViewById(R.id.btn_register2);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(registerActivity.this, "User was registered successfully!" , Toast.LENGTH_SHORT).show();
                                //progBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(registerActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    //makeCustomer(name, email);
                                   // String userID = auth.getCurrentUser().getUid();
                                    mCurrentUser= task.getResult().getUser();
                                    newUser=mDatabase.child("user"+Integer.valueOf(idNum));
                                    newUser.child("email").setValue(email);
                                    newUser.child("name").setValue(name);
                                    newUser.child("ID").setValue(idNum);
                                    //FirebaseMessaging.getInstance().subscribeToTopic("nearBuy");
                                    startActivity(new Intent(registerActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });


    }


public  void makeCustomer(String name, String Email){
        // need to delete PW from user
        this.customer.setName(name);
        this.customer.setEmail(Email);
        //this.customer.setID();
}



}
