package com.example.android.pointing.login;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abshafi on 9/8/2016.
 */
public class User {

    public String emailAddress;
    public String password;
    //public String studentID;
    public String username;
    public String studyGroupName;
    public String sgl;

    public User(){}

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getStudentID() {
//        return studentID;
//    }

//    public void setStudentID(String studentID) {
//        this.studentID = studentID;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudyGroupName() {
        return studyGroupName;
    }

    public void setStudyGroupName(String studyGroupName) {
        this.studyGroupName = studyGroupName;
    }

    public String isSgl() {
        return sgl;
    }

    public void setSgl(String sgl) {
        this.sgl = sgl;
    }
    public void equal(User user ){
        this.username= user.getUsername();
        this.password = user.getPassword();
        this.emailAddress=user.getEmailAddress();
        this.sgl=user.isSgl();
        this.studyGroupName=user.getStudyGroupName();
    }
    @Exclude
    public Map<String, Object> serialize(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("emailAddress", this.emailAddress);
        result.put("password", this.password);
//        result.put("studentID", this.studentID);
        result.put("username", this.username);
        result.put("studyGroupName", this.studyGroupName);
        result.put("sgl", this.sgl);
        return result;
    }
}