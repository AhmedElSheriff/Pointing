package com.example.android.pointing;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private EditText mEmail;
    private EditText mPassword;
    private TextView mNeedAccount;
    private Button mSignin;
    private Firebase mFirebase;

    private String mEmailStr;
    private String mPasswordStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Log.e("Token is ", FirebaseInstanceId.getInstance().getToken());

        mEmail = (EditText) rootView.findViewById(R.id.inemail);
        mPassword = (EditText) rootView.findViewById(R.id.inpassword);
        mNeedAccount = (TextView) rootView.findViewById(R.id.needaccount);
        mNeedAccount.setClickable(true);

        mSignin = (Button) rootView.findViewById(R.id.signin);

        mEmailStr = mEmail.getText().toString();
        mPasswordStr = mPassword.getText().toString();

        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User mUser = new User();
                mUser.setEmail(mEmailStr);
                mUser.setPassword(mPasswordStr);
            }
        });

        mNeedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SignupActivity.class));
            }
        });

        return rootView;
    }
}
