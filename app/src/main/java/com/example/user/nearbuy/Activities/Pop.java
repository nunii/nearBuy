package com.example.user.nearbuy.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.nearbuy.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.user.nearbuy.Activities.myListActivity.ListOfProds;

public class Pop extends Activity {

    ArrayAdapter<String> itemsAdapter;
    ListView lv;
    HashMap<String, String> itemByShop;
    private TextView az;
    private int sum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_list);
        az = findViewById(R.id.textView15);
        az.setVisibility(View.VISIBLE);
        sum = 0;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8) ,(int)(height*.6));
        lv = findViewById(R.id.listV);

        itemByShop = new HashMap<>();

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
        az.setTextColor(Color.parseColor("#FF0001"));
        az.setText(String.valueOf(sum));
        lv.setAdapter(adapter);

    }

    public void setItems(){

            for (int i = 0; i < myListActivity.prodslist.size(); i++) {

                final String theItem = ListOfProds.get(i).getItem();
                sum += Integer.parseInt(ListOfProds.get(i).getPrice());
                final String theShop = "chippest store is: " + ListOfProds.get(i).getStore()+".";
                final String thePrice = " price: "+ListOfProds.get(i).getPrice() +"NIS";
                itemByShop.put(theItem, theShop +thePrice);
            }
    }

    private void toastMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public interface MyCallback {
        void onCallback(String value);
    }
}
