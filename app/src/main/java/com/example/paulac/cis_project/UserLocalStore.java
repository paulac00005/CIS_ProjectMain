package com.example.paulac.cis_project;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by paulac on 2/22/16.
 */
public class UserLocalStore {

    public static final String SP = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context con) {
        userLocalDatabase = con.getSharedPreferences(SP, 0);
    }

    public void storedUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("key", user.key);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        String key = userLocalDatabase.getString("key", "70930f27");

        User storedUser = new User(username, password, key);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn", false) == true){
            return true;
        }else{
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
