package com.example.android.pointing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupActivityFragment extends Fragment {

    public SignupActivityFragment() {
    }

    private EditText mEmail;
    private EditText mPassword;
    private Button mSignup;
    private Firebase mFirebase;

    private String mEmailStr;
    private String mPasswordStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_signup, container, false);

        mEmail = (EditText) rootView.findViewById(R.id.upemail);
        mPassword = (EditText) rootView.findViewById(R.id.uppassword);
        mSignup = (Button) rootView.findViewById(R.id.signup);
        mFirebase.setAndroidContext(getContext());
        mFirebase = new Firebase("https://pointings-2264c.firebaseio.com/");


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailStr = mEmail.getText().toString();
                mPasswordStr = mPassword.getText().toString();

                final User mUser = new User();
                mUser.setEmail(mEmailStr);
                mUser.setPassword(mPasswordStr);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mUser.getEmail(),mUser.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()){
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
