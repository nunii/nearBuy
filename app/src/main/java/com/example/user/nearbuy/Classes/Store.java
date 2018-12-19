package com.example.user.nearbuy.Classes;


import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amit Nuni
 * This class represents a Store.
 * */


import java.util.HashMap;
import java.util.Map;

public class Store implements mainStore {

    private String owner, category,name;
    static int ID=0;
    private Map<String, Integer> products = new HashMap<>();


    public Store(String name, String category, String owner) throws IOException{
        this.name=name;
        this.category=category;
        this.owner=owner;
        ID+=1;
        //addStoreToDB(ID,name);
    }


    /**
     * Adds a product with price to the products map.
     * */
    public void addProduct(String product, int price){
        products.put(product,price);
    }

    /**
     * Sets an existing product price.
     * */
    public void setProductPrice(String product, int price){
        products.put(product,price);
    }

    /**
     * @return product's price
     * */
    public int getProductPrice(String product){
       return products.get(product);
    }

    /**
     * @return store's name
     * */
    public String getName(){
        return name;
    }

    /**
     * @return store's category
     * */
    public String getCategory(){
        return category;
    }

    /**
     * @return store's ID
     * */
    public int getID(){
        return ID;
    }

    /**
     * @return store's owner
     * */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets owner's name
     * */
    public void setOwner(String owner) {
        this.owner = owner;
    }

   /* public static void main(String[] args) throws IOException{
    *//*    Store a = new Store("sason vebanav", "food","nuni");
        a.addProduct("Falafel", 10);
        a.addProduct("water", 4);
        System.out.println(a.getProductPrice("water"));
        a.setProductPrice("water", 443);
        System.out.println(a.getProductPrice("water"));*//*
        addStoreToDB(2,"Aflalo vebanab");
    }*/
}
