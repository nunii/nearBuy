package com.example.user.nearbuy.Classes;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amit Nuni
 * This class represents a Store.
 * */


import java.util.HashMap;
import java.util.Map;

public class Store  {

    private String Category,Name,City;
    HashMap<String,Integer> Items;

    public Store(){
        Items = new HashMap<String,Integer>();
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void addItem(String item, int val){
        this.Items.put(item,val);
    }

    public HashMap<String, Integer> getItems() {
        return Items;
    }

    public int getVal(String key){
        return this.Items.get(key);
    }

    /*public String getItem(String name){
       String ans ="";

       for(String temp :this.Items){
           if (temp.matches(name)){
               ans = name;
           }
       }
       return ans;
    }*/

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

}
