/*
package com.example.user.nearbuy.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.nearbuy.Classes.Store;
import com.example.user.nearbuy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    private int cityPos,categoryPos,productPos,itemPos;
    private ArrayList<String> list;
    private DatabaseReference mDatabase;
    private static Map<String,Integer> BURGERKING,ACE,RENUAR,ZARA = new HashMap<>();
    private String city,category,product;
    Spinner productsSpinner,citySpinner,categorySpinner;
    ArrayAdapter<CharSequence> categoryAdapter2,cityAdapter,productsAdptr;
    private RecyclerView itemsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Stores");
        list = new ArrayList<>();

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


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                //position=0;
                city = parentView.getSelectedItem().toString();
                if(position!=0){
                    cityPos = position;
                    //Toast.makeText(getApplicationContext(), "city is: "+city, Toast.LENGTH_SHORT).show();
                }
                */
/*else{
                    Toast.makeText(getApplicationContext(), "city is: "+city, Toast.LENGTH_SHORT).show();
                }*//*

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
                    //itemPos = productsSpinner.getSelectedItemPosition();
                    //Toast.makeText(getApplicationContext(), "category is: "+category, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "item is: "+product, Toast.LENGTH_SHORT).show();
                }
            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                city = "";
                //return;
            }
        });


    }


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
            //productsAdptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
            Toast.makeText(getApplicationContext(), "There are no such items in: "+city, Toast.LENGTH_SHORT).show();
        }

    }

    public void addItem(View v){

    }
}
*/
