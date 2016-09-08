package com.example.android.pointing;

import com.firebase.client.Firebase;

/**
 * Created by Abshafi on 9/8/2016.
 */
public class User {

    String email,password, id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }



    public String getPassword() {
        return password;
    }

    public void saveUser()
    {
        Firebase mFirebase = new Firebase("https://pointings-2264c.firebaseio.com/");
        mFirebase = mFirebase.child("users").child(getId());
        mFirebase.setValue(this);
    }
}
