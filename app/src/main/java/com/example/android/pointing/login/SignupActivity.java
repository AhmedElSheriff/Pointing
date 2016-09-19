package com.example.android.pointing.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.pointing.R;

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
