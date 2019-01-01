package com.example.user.nearbuy.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.user.nearbuy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Pop extends Activity {

    ArrayAdapter<String> itemsAdapter;
    ListView lv;
    HashMap<String, String> itemByShop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_list);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) ,(int)(height*.6));
        lv = findViewById(R.id.listV);
        //itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myListActivity.prodslist);
        //lv.setAdapter(itemsAdapter);


        itemByShop = new HashMap<>();
        //itemByShop.put("Diana", "3214 Broadway Avenue");
/*        itemByShop.put("Tyga", "343 Rack City Drive");
        itemByShop.put("Rich Homie Quan", "111 Everything Gold Way");
        itemByShop.put("Donna", "789 Escort St");
        itemByShop.put("Bartholomew", "332 Dunkin St");
        itemByShop.put("Eden", "421 Angelic Blvd");*/
        setItems();

        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.costume_row,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        Iterator it = itemByShop.entrySet().iterator();
        while (it.hasNext())
        {
            HashMap<String, String> resultsMap = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultsMap.put("First Line", pair.getKey().toString());
            resultsMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultsMap);
        }

        lv.setAdapter(adapter);

    }

    public void setItems(){
        //Iterator itr = myListActivity.prodslist.iterator();
        //int counter = 0;
        if(myListActivity.prodslist.size() == myListActivity.storesss.size()){
            for (int i = 0; i < myListActivity.prodslist.size(); i++) {
                itemByShop.put(myListActivity.prodslist.get(i), "chippest store is: " + myListActivity.storesss.get(i));
            }
        }
        else{
            toastMsg("lists are not equal.");
        }
    }

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
