package com.example.android.pointing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_signup_container, new SignupActivityFragment()).commit();
        }

    }

}
