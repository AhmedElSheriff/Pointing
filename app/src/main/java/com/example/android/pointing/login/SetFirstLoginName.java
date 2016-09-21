package com.example.android.pointing.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.pointing.MainActivity;
import com.example.android.pointing.R;

public class SetFirstLoginName extends AppCompatActivity {


    private EditText mUsername;
    private Button mOkButton;

    private String mUsernameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_first_login_name);

        Toast.makeText(this,"Please enter your username: ",Toast.LENGTH_SHORT).show();

        mUsername = (EditText) findViewById(R.id.firstloginametext);
        mOkButton = (Button) findViewById(R.id.firstloginnamebutton);

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUsernameStr = mUsername.getText().toString();

                Intent intent = new Intent(SetFirstLoginName.this, MainActivity.class);
                intent.putExtra("username", mUsernameStr);
                startActivity(intent);
            }
        });


    }
}
