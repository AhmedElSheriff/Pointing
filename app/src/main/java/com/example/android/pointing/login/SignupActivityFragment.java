package com.example.android.pointing.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pointing.R;
import com.example.android.pointing.controller.ControllerSGL;
import com.example.android.pointing.controller.ControllerStudent;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//import com.example.android.pointing.ControllerSGL;
//import com.example.hima.pointingapp.controller.ControllerStudent;

/**
 * A placeholder fragment containing a simple view.
 *
 *
 */


public class SignupActivityFragment extends Fragment implements View.OnClickListener {

    public SignupActivityFragment() {
    }


    private EditText mEmail;
    private EditText mPassword;
    private EditText mUsername;
    private Button mSignup;
    private Firebase mFirebase;
    private Spinner mStudyGroupNamesSpinner;
    private ArrayAdapter<String> mArrayAdapter;
    private String[] mStudyGroupNamesArray;
    private CheckBox mSglCheckBox;
    private User mUser;

    private final String FIREBASE_URL="https://pointingapp-dda9e.firebaseio.com/";
    private String mEmailStr;
    private String mPasswordStr;
    private String mUsernameStr;
    private String mStudyGroupName;
    private boolean mIsSgl;
    private String SGL;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = new User();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("FIREBASE", "onAuthStateChanged:signed_in:" + user.getUid());

                    //call to generate user using Uid instead of pushID
                    generateUser(mUser, user.getUid());
                    // startActivity(new Intent(getActivity(), MainActivity.class));
                } else {
                    // User is signed out
                    Log.d("FIREBASE", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        mEmail = (EditText) rootView.findViewById(R.id.upemail);
        mPassword = (EditText) rootView.findViewById(R.id.uppassword);
        mUsername = (EditText) rootView.findViewById(R.id.username);
        mSignup = (Button) rootView.findViewById(R.id.signup);
        mSglCheckBox = (CheckBox) rootView.findViewById(R.id.sglcheckbox);
        mStudyGroupNamesSpinner = (Spinner) rootView.findViewById(R.id.studygroupnamesspinner);

        mStudyGroupNamesArray = getResources().getStringArray(R.array.Study_Group_Names);

        mArrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.study_group_layout,R.id.studygroupnametextview,mStudyGroupNamesArray);
        mStudyGroupNamesSpinner.setAdapter(mArrayAdapter);
        mStudyGroupNamesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStudyGroupName = mStudyGroupNamesArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFirebase.setAndroidContext(getContext());
        mFirebase = new Firebase(FIREBASE_URL);
        mSignup.setOnClickListener(this);

        return rootView;
    }

    public void generateUser(User user, String uid) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        String userType;
        String allusers = "allUsers/" + uid;
        Map<String, Object> newUserUpdate = new HashMap<>();
        if (user.getUsername() != null) {
            if (user.isSgl()=="true") {
                userType = user.getStudyGroupName() + "/" + "SGL" + "/" + uid;
            } else {
                userType = user.getStudyGroupName() + "/" + "Student" + "/" + uid;
            }

            newUserUpdate.put(userType, user.serialize());
            newUserUpdate.put(allusers, user.serialize());
            database.updateChildren(newUserUpdate);
        }
    }
    public void registerUser(){
        mEmailStr = mEmail.getText().toString().trim();
        mPasswordStr = mPassword.getText().toString().trim();
        mUsernameStr = mUsername.getText().toString().trim();
        mIsSgl = mSglCheckBox.isChecked();
        if(TextUtils.isEmpty(mEmailStr)){
            Toast.makeText(getActivity(),"Please Enter Email!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mPasswordStr)){
            Toast.makeText(getActivity(),"Please Enter Password!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mUsernameStr)){
            Toast.makeText(getActivity(),"Please Enter UserName!!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mIsSgl){
            SGL ="true";
        }
        else{
            SGL ="false";
        }
        mUser.setEmailAddress(mEmailStr);
        mUser.setPassword(mPasswordStr);
        mUser.setSgl(SGL);
        mUser.setStudyGroupName(mStudyGroupName);
        mUser.setUsername(mUsernameStr);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mUser.getEmailAddress(), mUser.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getActivity(), "تم تسجيلك بنجاح ^_^ ", Toast.LENGTH_SHORT).show();
                    if(mUser.isSgl().equals("true")){
                        FirebaseUser user = task.getResult().getUser();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mUser.getUsername())
                                .build();
                        user.updateProfile(userProfileChangeRequest);
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), ControllerSGL.class));
                    }
                    else{
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), ControllerStudent.class));
                    }

                } else {
                    Toast.makeText(getActivity(), "فشل التسجيل", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == mSignup){
            registerUser();
        }
    }

public class YourItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selected = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}