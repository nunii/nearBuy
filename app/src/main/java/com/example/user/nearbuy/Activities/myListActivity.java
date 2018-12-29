package com.example.user.nearbuy.Activities;
///https://stackoverflow.com/questions/2250770/how-to-refresh-android-listview
import android.app.ListActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    private static ArrayList<Store> tList;
    private static int minPrice;
    private static String finalCity;

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
                        String key = "";
                        int val = 0;
                        Store store = new Store();
                        store.setName(ds.child("Name").getValue(String.class));
                        store.setCity(ds.child("City").getValue(String.class));
                        store.setCategory(ds.child("Category").getValue(String.class));
                        for (DataSnapshot item : ds.child("Items").getChildren()){
                            key = item.getKey();
                            val = item.getValue(Integer.class);
                            store.addItem(key,val);
                        }
                        storesList.add(store);
                        //toastMsg(String.valueOf(storesList.get(0).getVal("hammer")));
                    }
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mRef.addValueEventListener(vEventListener);

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
                makeList();
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
    }

    private void makeList() {
        minPrice = 100000;
        tList = new ArrayList<>();
        for(Store st : storesList){
            if(st.getCity()==city){
                tList.add(st);
            }
        }
        for(Store st: tList){
            if(!st.getItems().containsKey(product)){
                tList.remove(st);
            }
        }
        for(Store st: tList){
            if(st.getItems().get(product)<minPrice){
                minPrice = st.getItems().get(product);
                finalCity = st.getCity();
            }
        }
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

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
