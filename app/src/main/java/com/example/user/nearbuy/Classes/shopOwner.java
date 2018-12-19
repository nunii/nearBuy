package com.example.user.nearbuy.Classes;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class shopOwner extends Customer {

    private Set<Integer> stores;
    private int balance;
    private List<Store> Stores;


    public shopOwner(String name, String password, String email) throws IOException {
        super.name=name;
        super.password=password;
        super.ID=1+ID;
        super.email=email;
        //addUserToDB(name,password);
    }

    public void createShop(String shopName, String category) throws IOException{
        Store t = new Store(shopName,category,this.name);
    }





}
