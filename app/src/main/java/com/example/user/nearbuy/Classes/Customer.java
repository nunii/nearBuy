package com.example.user.nearbuy.Classes;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    public String name;
    public static int ID=0;
    protected String email, password;
    protected List<Integer> favStores;
    // need to implement friends.

    public Customer(){
        this.ID=1+ID;
    }

    public Customer(String name, String password, String email) throws IOException{
       this.name=name;
       this.password=password;
       this.ID=1+ID;
       this.email=email;
       //addUserToDB(name,password);
    }


    /*public List<String> makeShopingList(String... args){
       List<String> shopingList = new ArrayList<String>();
        for (String arg : args) {
           shopingList.add(arg);
        }
        return shopingList;
    }*/

    public void addFavStore(int storeID){
        favStores.add(storeID);
    }

    public static String getIdAsString(){return String.valueOf(Customer.ID);}

    public String getName() {
        return name;
    }

    public void setPW(String pw) {
        this.password = pw;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public void setID(){
        ID+=1;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getFavStores() {
        return favStores;
    }


}
