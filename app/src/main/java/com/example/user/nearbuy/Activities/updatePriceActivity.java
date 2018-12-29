package com.example.user.nearbuy.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.nearbuy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updatePriceActivity extends AppCompatActivity {

    private Button btn_update;
    private EditText et_city,et_price,et_item,et_store;
    private String city,store,item;
    private int price;
    private DatabaseReference mRef;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_price);

        btn_update = findViewById(R.id.btn_updatePriceNow);

        et_city = findViewById(R.id.et_city);
        et_store = findViewById(R.id.et_store);
        et_item = findViewById(R.id.et_item);
        et_price = findViewById(R.id.et_price);

        flag = false;

        mRef = FirebaseDatabase.getInstance().getReference().child("Stores");



    }

    public void updatePriceNow(View v){
        if(v.getId() == R.id.btn_updatePriceNow){
            city = et_city.getText().toString();
            store = et_store.getText().toString().toUpperCase();
            item = et_item.getText().toString();
            price = Integer.parseInt(et_price.getText().toString());
            flag = true;
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mRef.child(store).child("Items").child(item).setValue(price);
                    flag = false;
                    toastMsg("price updated!");
                    return;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
