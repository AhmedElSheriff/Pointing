package com.example.android.pointing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private EditText mEmail;
    private EditText mPassword;
    private EditText mUsername;
    private TextView mNeedAccount;
    private Button mSignin;
    private Firebase mFirebase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private String mEmailStr;
    private String mPasswordStr;
    private String mUsernameStr;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mEmail = (EditText) rootView.findViewById(R.id.inemail);
        mPassword = (EditText) rootView.findViewById(R.id.inpassword);
        mSignin = (Button) rootView.findViewById(R.id.signin);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getActivity().getIntent();
        mUsernameStr = intent.getStringExtra("username");

        mNeedAccount = (TextView) rootView.findViewById(R.id.needaccount);
        mNeedAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SignupActivity.class));
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d("Firebase", "onAuthStateChanged:signed_in:" + user.getUid());

                    startActivity(new Intent(getActivity(),SetFirstLoginName.class));

                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mUsernameStr)
                            .build();
                    user.updateProfile(userProfileChangeRequest);
                }
                else
                {
                    Log.d("Firebase", "onAuthStateChanged:signed_out");
                }
            }
        };


        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailStr = SignupActivityFragment.removeSpaces(mEmail.getText().toString());
                mPasswordStr = mPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(mEmailStr, mPasswordStr).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = task.getResult().getUser();

//                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(mUsernameStr)
//                                    .build();
//                            user.updateProfile(userProfileChangeRequest);

                            Toast.makeText(getActivity(), "تم تسجيلك بنجاح ^_^ ", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getActivity(), "فشل التسجيل", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        return rootView;
    }
}
