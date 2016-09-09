package com.example.android.pointing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupActivityFragment extends Fragment {

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

    private String mEmailStr;
    private String mPasswordStr;
    private String mUsernameStr;
    private String mStudyGroupName;
    private boolean mIsSgl;
   // private boolean isSigned = false;

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

        mIsSgl = mSglCheckBox.isChecked();

        mFirebase.setAndroidContext(getContext());
        mFirebase = new Firebase("https://pointings-2264c.firebaseio.com/");


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mEmailStr = removeSpaces(mEmail.getText().toString());
                mPasswordStr = mPassword.getText().toString();
                mUsernameStr = mUsername.getText().toString();


                final User mUser = new User();
                mUser.setEmail(mEmailStr);
                mUser.setPassword(mPasswordStr);


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mUser.getEmail(), mUser.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "تم تسجيلك بنجاح ^_^ ", Toast.LENGTH_SHORT).show();
                            generateUser(mUser, mUsernameStr, mPasswordStr, mEmailStr, mStudyGroupName, mIsSgl);
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "فشل التسجيل", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

        return rootView;
    }

    public void generateUser(User user, String username, String password, String email, String studyGroupName, boolean isSgl)
    {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference users;
        if(isSgl)
        {
           users = database.getReference(studyGroupName).child("SGL");
        }
        else
        {
            users = database.getReference(studyGroupName).child("Student");
        }
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        if(isSgl)
        {
            user.setIsSgl("The user is SGL");
        }
        else
        {
            user.setIsSgl("The user isn't SGL");
        }
        users.push().setValue(user);

    }

    public static String removeSpaces(String email)
    {
        return email.replaceAll("\\s+","");
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
