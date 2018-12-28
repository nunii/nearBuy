package com.example.user.nearbuy.Activities;
///https://stackoverflow.com/questions/2250770/how-to-refresh-android-listview
import android.app.ListActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.nearbuy.Classes.Store;
import com.example.user.nearbuy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class myListActivity extends AppCompatActivity {

    private int cityPos,categoryPos,productPos,itemPos,sum,numItems;
    private ArrayList<Store> storesList;
    private ArrayList<String> prodslist;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private Firebase mRef;
    private String storeID;
    private static Map<String,Integer> BURGERKING,ACE,RENUAR,ZARA = new HashMap<>();
    private String city,category,product;
    private DataSnapshot ds;
    Spinner productsSpinner,citySpinner,categorySpinner;
    ArrayAdapter<CharSequence> categoryAdapter2,cityAdapter,productsAdptr;
    ArrayAdapter<String> prodsAdptr;
    ListView lv;
    Button addItemBTN,mkList;
    Query mQuery;

    String temp; // kill after.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        storesList = new ArrayList<>();
        //mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Stores");
        //mRef.addListenerForSingleValueEvent(valueEventListener);
        //Query query = mRef.equalTo("ACE");

       /* Query query = FirebaseDatabase.getInstance().getReference("Stores")
                .orderByChild("Name")
                .equalTo("ACE");*/

        ValueEventListener vEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storesList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Store store = new Store();
                        //Store.setName(ds.child("Name").getValue(Store.class).getName());
                        //String sname = ds.child("Name").getValue(String.class);
                        store.setName(ds.child("Name").getValue(String.class));
                        store.setCity(ds.child("City").getValue(String.class));
                        store.setCategory(ds.child("Category").getValue(String.class));

                        /*sInfo.setCategory(ds.child(userID).getValue(storeInformation.class).getCategory());
                        sInfo.setCity(ds.child(userID).getValue(storeInformation.class).getCity());*/

                        storesList.add(store);
                        //Artist artist = snapshot.getValue(Artist.class);
                        //artistList.add(artist);
                    }
                    //adapter.notifyDataSetChanged();
                }
                 //dataSnapshot.child("ACE").child("City").getValue(String.class);
                temp = storesList.get(1).getName();
                if (storesList.isEmpty()) temp = "empty";
                toastMsg(temp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addValueEventListener(vEventListener);


  /*      mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.child("ACE").child("City").getValue(String.class);
                toastMsg(temp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

       /* FirebaseUser user = mAuth.getCurrentUser();*/
        //storeID = mDatabase.child("ACE").toString();
        //storeID = mDatabase.child("City").toString();
        //ds = mDatabase.child("City");
        //toastMsg(storeID);

        /*userID = user.get;
        toastMsg(userID);*/

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("test", "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMsg("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("test", "onAuthStateChanged:signed_out");
                    toastMsg("Successfully signed out.");
                }
                // ...
            }
        };*/

        //mAuth.addAuthStateListener(mAuthListener);
        lv = findViewById(R.id.list_);
        prodslist = new ArrayList<>();
        numItems=0;

        mkList = (Button) findViewById(R.id.btn_mklist);
        addItemBTN = (Button) findViewById(R.id.btn_addItem);

        prodsAdptr = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prodslist);
        lv.setAdapter(prodsAdptr);

        citySpinner = findViewById(R.id.spinner_city);
        categorySpinner = findViewById(R.id.spinner_category);
        productsSpinner = findViewById(R.id.spinner_products);

        cityAdapter = ArrayAdapter.createFromResource(this,R.array.cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        categoryAdapter2 = ArrayAdapter.createFromResource(this,R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter2);

        productsAdptr = ArrayAdapter.createFromResource(this,R.array.empty_items, android.R.layout.simple_spinner_item);
        productsAdptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating the querry.
                //setQuerry(city,category,product);
                // need to create a global string which will be used on that popup.
                startActivity(new Intent(myListActivity.this,Pop.class));
                // should add progress bar on that activity.

            }
        });


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                city = parentView.getSelectedItem().toString();
                if(position!=0){
                    cityPos = position;
                }

            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                city = "";
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                category = parentView.getSelectedItem().toString();
                if(position!=0){
                    categoryPos = position;
                    setItemSpinner();
                }
            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                city = "";
                //return;
            }
        });

        productsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                product = parentView.getSelectedItem().toString();
                if(position!=0){
                    product = parentView.getSelectedItem().toString();
                    toastMsg("item is: "+product);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                city = "";
                //return;
            }
        });

       /* mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                *//*for(DataSnapshot data: dataSnapshot.getChildren()){
                    // use array1.add to store value in your arrayList
                    toastMsg(dataSnapshot.child("City").getValue(String.class));

                }*//*
               *//* String temp = dataSnapshot.child("RENUAR").child("City").getValue(String.class);
                if(temp == "Herzlia") {
                    toastMsg(temp);
                }
                else toastMsg("not herzlia!!!!!!!!");*//*
               //toastMsg(dataSnapshot.child("ACE").child("Items").child("hammer").getValue(String.class));
                *//*int temp = dataSnapshot.child("ACE").child("Items").child("hammer").getValue(Integer.class);
                toastMsg(String.valueOf(temp));*//*
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }

   /* private void setQuerry(String city, String category, String product) {

    }*/


    public void setItemSpinner(){
        if(cityPos == 1 && categoryPos == 1){
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.herzilia_items, android.R.layout.simple_spinner_item);
            productsSpinner.setAdapter(productsAdptr);
            productsAdptr.notifyDataSetChanged();
        }
        else if(cityPos == 0 && categoryPos == 0){

        }
        else if(cityPos == 0 || categoryPos == 0){

        }
        else if(cityPos == 2 && categoryPos == 3){
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.hod_hasharon_items, android.R.layout.simple_spinner_item);
            productsSpinner.setAdapter(productsAdptr);
            productsAdptr.notifyDataSetChanged();
        }
        else if(cityPos == 3 && categoryPos == 2){
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.petah_tiqwa_food_items, android.R.layout.simple_spinner_item);
            productsSpinner.setAdapter(productsAdptr);
            productsAdptr.notifyDataSetChanged();
        }
        else if(cityPos == 3 && categoryPos == 3){
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.petah_tiqwa_clothing_items, android.R.layout.simple_spinner_item);
            productsSpinner.setAdapter(productsAdptr);
            productsAdptr.notifyDataSetChanged();
        }
        else{
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.empty_items, android.R.layout.simple_spinner_item);
            productsSpinner.setAdapter(productsAdptr);
            productsAdptr.notifyDataSetChanged();
            toastMsg("There are no such items in: "+city);
        }
    }

    public void addItem(View v){

        prodslist.add(product);
        numItems++;
        prodsAdptr.notifyDataSetChanged();
        toastMsg("item Added "+prodslist.get(prodslist.size()-1));
    }

/*    private void showData(DataSnapshot DataSnapshot){
        for(DataSnapshot ds : DataSnapshot.getChildren()){
            storeInformation sInfo = new storeInformation();
            sInfo.setName(ds.child(userID).getValue(storeInformation.class).getName());
            sInfo.setCategory(ds.child(userID).getValue(storeInformation.class).getCategory());
            sInfo.setCity(ds.child(userID).getValue(storeInformation.class).getCity());
            //sInfo.setItems(ds.child(userID).getValue(storeInformation.class).getItems());
            // need to figure out how to enter the data into a hashmap
            Log.d("test", "showData: city"+sInfo.getCity());
            Log.d("test", "showData: category"+sInfo.getCategory());
            Log.d("test", "showData: name"+sInfo.getName());

            ArrayList<String> array = new ArrayList<>();
            array.add(sInfo.getCity());
            array.add(sInfo.getName());
            array.add(sInfo.getCategory());
        }
    }*/

   /* @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }*/

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
