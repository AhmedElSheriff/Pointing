package com.example.hima.pointingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hima.pointingapp.controller.ControllerSGL;
import com.example.hima.pointingapp.controller.ControllerStudent;
import com.example.hima.pointingapp.login.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }


    private EditText mEmail;
    private EditText mPassword;
    private TextView mNeedAccount;
    private Button mSignin;
    private FirebaseAuth mAuth;
    private String mEmailStr;
    private String mPasswordStr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mEmail = (EditText) rootView.findViewById(R.id.inemail);
        mPassword = (EditText) rootView.findViewById(R.id.inpassword);
        mSignin = (Button) rootView.findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        mNeedAccount = (TextView) rootView.findViewById(R.id.needaccount);
        mNeedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SignupActivity.class));
            }
        });


        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmailStr = mEmail.getText().toString().trim();
                mPasswordStr = mPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(mEmailStr, mPasswordStr).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String mUser = user.getDisplayName();
                            if(mUser!=null){
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), ControllerSGL.class));
                            }
                            else{
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), ControllerStudent.class));
                            }
                        }else {
                            Toast.makeText(getActivity(), "Not Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return rootView;
    }


}
/*
//   getExistingUserInfo();
                          */
/*  if(User!=null) {
                           *//*
*/
/*  //   Toast.makeText(getActivity(), "2aaaaaaablny !!!!!!", Toast.LENGTH_SHORT).show();
                                Log.v("HimaAbousalem",userKey);*//*
*/
/*
                                sglstr = User.get("sgl");
                                if (sglstr.equals("true")) {
                                    Intent intent = new Intent(getActivity(), ControllerSGL.class);
                                    intent.putExtra("user", User);
                                    startActivity(intent);
                                } else if (sglstr.equals("false")) {
                                    Intent intent = new Intent(getActivity(), ControllerStudent.class);
                                    intent.putExtra("user", User);
                                    startActivity(intent);
                                }
                               Intent intent = new Intent(getActivity(), ControllerManager.class);
                                intent.putExtra("userKey", userKey);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getActivity(), "NULL!!!!!!", Toast.LENGTH_SHORT).show();
                            }*//*

Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ControllerManager.class);
        intent.putExtra("userKey", userKey);
        startActivity(intent);*/

/*
    */
/*    public void getExistingUserInfo(String uid){

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference("allUsers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //get user data from dataSnapshot
               //    Toast.makeText(getContext(),"Userkey: " + userKey,Toast.LENGTH_LONG).show();
                 //   Log.v("himaAbousalem",userKey);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }*//*

    public void getExistingUserInfo(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("allUsers").orderByChild("emailAddress")
                .equalTo(mEmailStr)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            uid = childSnapshot.getKey();
                            Log.v("HimaAbosalem",uid);

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
   */
/*     database.getReference("allUsers").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get user data from dataSnapshot
                User=(HashMap<String,String>)dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*//*


    }*/
