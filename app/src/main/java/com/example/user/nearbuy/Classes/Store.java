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
    ArrayList<String> Items;

    public Store(){

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

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public ArrayList<String> getItems() {
        return Items;
    }

    public void setItems(ArrayList<String> items) {
        Items = items;
    }


}
