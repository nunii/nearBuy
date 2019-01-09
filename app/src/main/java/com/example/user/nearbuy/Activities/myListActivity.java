package com.example.user.nearbuy.Activities;
///https://stackoverflow.com/questions/2250770/how-to-refresh-android-listview
import android.app.ListActivity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.nearbuy.Classes.Product;
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
    public static ArrayList<String> prodslist;
    public static ArrayList<String> storesss;
    public static Map<String, Integer> prices1 = new HashMap<>();
    private DatabaseReference mRef;
    private String storeID;
    private String city,category,product;
    private DataSnapshot ds;
    Spinner productsSpinner,citySpinner,categorySpinner;
    ArrayAdapter<CharSequence> categoryAdapter2,cityAdapter,productsAdptr;
    ArrayAdapter<String> prodsAdptr;
    ListView lv;
    Button addItemBTN,mkList;
    private static ArrayList<Store> tList;
    private static int minPrice;
    private static String finalCity;
    public static String chippest_store;
    private ValueEventListener vEventListener;
    private ProgressBar mProgressBar;
    private int mProgressStatus = 0;
    private TextView mLoadingText;
    private Handler mHandler = new Handler();
    public static ArrayList<Product> ListOfProds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLoadingText = (TextView) findViewById(R.id.loadingText);
        storesList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("Stores");
        ListOfProds = new ArrayList<>();
        chippest_store = "";
       /* Query query = FirebaseDatabase.getInstance().getReference("Stores")
                .orderByChild("Name")
                .equalTo("ACE");*/

         vEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                minPrice = 100000;
                if (dataSnapshot.exists()) {
                    for(int i = 0; i<ListOfProds.size();i++) {
                        minPrice = 100000;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot item : ds.child("Items").getChildren()) {
                                if (item.getKey().equals(ListOfProds.get(i).getItem())) {
                                    if (item.getValue(Integer.class) < minPrice) {
                                        minPrice = item.getValue(Integer.class);
                                        chippest_store = ds.child("Name").getValue(String.class);
                                    }
                                }
                            }
                        }
                        ListOfProds.get(i).setPrice(String.valueOf(minPrice));
                        ListOfProds.get(i).setStore(chippest_store);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        lv = findViewById(R.id.list_);
        prodslist = new ArrayList<>();
        storesss = new ArrayList<>();
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
                mRef.addValueEventListener(vEventListener);
                mProgressBar.setVisibility(View.VISIBLE);
                mLoadingText.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgressStatus < 100){
                            mProgressStatus++;
                            android.os.SystemClock.sleep(50);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mProgressStatus);
                                }
                            });
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(myListActivity.this,Pop.class));
                                mLoadingText.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }).start();
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
            }
        });

        productsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                product = parentView.getSelectedItem().toString();
                if(position!=0){
                    product = parentView.getSelectedItem().toString();
                    //toastMsg("item is: "+product);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView)
            {
                city = "";
            }
        });

    }

    public void setItemSpinner(){
        if(cityPos == 1 && categoryPos == 1){
            productsAdptr = ArrayAdapter.createFromResource(this,R.array.herzliya_items, android.R.layout.simple_spinner_item);
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
        Product p = new Product();
        p.setItem(product);

        ListOfProds.add(p);
        numItems++;
        prodsAdptr.notifyDataSetChanged();
    }

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
