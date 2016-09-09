package com.example.android.pointing;

import com.firebase.client.Firebase;

/**
 * Created by Abshafi on 9/8/2016.
 */
public class User {

    String email,password, id, username, studyGroupName;
    String isSgl;

    public void setIsSgl(String isSgl) {
        this.isSgl = isSgl;
    }

    public String isSgl() {
        return isSgl;
    }

    public void setStudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getUsername() {
        return username;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public void saveUser()
    {
        Firebase mFirebase = new Firebase("https://pointings-2264c.firebaseio.com/");
        mFirebase = mFirebase.child("users").child(getId());
        mFirebase.setValue(this);
    }
}
