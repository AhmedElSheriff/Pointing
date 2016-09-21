package com.example.android.pointing.ui.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.pointing.R;
import com.example.android.pointing.db.NewActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddActivity extends Fragment {

    public AddActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Spinner mSpinner;
    private EditText mURL;
    private Button mAddActivityBtn;
    public String mURLStr;
    HashMap<String,String> userMap;
    HashMap<String,NewActivity> activitiesMap;
    List<NewActivity> posts;
    String studyGroupName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayAdapter<NewActivity> adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addactivity, container, false);



        getUserInfo();

        mSpinner = (Spinner) rootView.findViewById(R.id.allactivities);
        mURL = (EditText) rootView.findViewById(R.id.urltext);
        mAddActivityBtn = (Button) rootView.findViewById(R.id.addactivitybtn);




        mAddActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mURLStr = mURL.getText().toString();

            }
        });

        return rootView;
    }

    public void getUserInfo()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        database.getReference("allUsers").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userMap = (HashMap<String, String>) dataSnapshot.getValue();

                studyGroupName = userMap.get("studyGroupName");

                List<String> posts = new ArrayList<>(userMap.values());

                for (String post : posts) {
                    Log.e("Post Title", post);
                }


                getActivties();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getActivties()
    {


        database.getReference(studyGroupName).child("SGActivties").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                activitiesMap = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, NewActivity>>() {
                });

                posts = new ArrayList<>(activitiesMap.values());

                for (NewActivity post : posts) {
                    Log.e("Post Title", post.getActivityName());
                }

                adapter = new ArrayAdapter<NewActivity>(getActivity(),android.R.layout.simple_list_item_1,posts);
                mSpinner.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
