package com.example.user.nearbuy.Activities;

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

public class saleActivity extends AppCompatActivity {

    private Button btn_update;
    private EditText et_city,et_discount,et_item,et_store;
    private String city,store,item;
    private int discount;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);


        btn_update = findViewById(R.id.btn_updateprice);

        et_city = findViewById(R.id.et_city2);
        et_store = findViewById(R.id.et_store2);
        et_item = findViewById(R.id.et_item2);
        et_discount = findViewById(R.id.et_discount);


        mRef = FirebaseDatabase.getInstance().getReference().child("Stores");
    }

    public void updateSale(View v){
        if(v.getId() == R.id.btn_updateprice){
            city = et_city.getText().toString();
            store = et_store.getText().toString().toUpperCase();
            item = et_item.getText().toString();

            // need to fix from here
            discount = Integer.parseInt(et_discount.getText().toString());
            //flag = true;
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String the_item = mRef.child(store).child("Items").child(item).toString();
                    int t = dataSnapshot.child(store).child("Items").child(item).getValue(Integer.class);
                    if (the_item=="all") {
                        for(DataSnapshot ds : dataSnapshot.child(store).child("Items").getChildren()){
                            //flag = false;
                            //doesnt work yet.
                            mRef.child(store).child("Items").child(ds.toString()).setValue(t*discount/100);
                            toastMsg("store updated!");
                            return;
                        }
                    }
                    else if(the_item == ""){

                    }
                    else{
                        mRef.child(store).child("Items").child(item).setValue(t*discount/100);
                        toastMsg("price updated!");
                        return;
                    }
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
