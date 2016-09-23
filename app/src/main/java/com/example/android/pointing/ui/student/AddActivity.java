package com.example.android.pointing.ui.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.pointing.R;
import com.example.android.pointing.db.NewActivity;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    HashMap<String, String> userMap;
    HashMap<String, NewActivity> activitiesMap;
    List<NewActivity> posts;
    String studyGroupName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayAdapter<NewActivity> adapter;
    protected String userID;
    protected HashMap<String,NewActivity> activitiesMap2;

    protected ProgressDialog mProgressDialog;
    protected String selectedActivity;

    //NewActivity mNewActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addactivity, container, false);

        Firebase.setAndroidContext(getContext());

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please Wait!");


        getUserInfo();

        mSpinner = (Spinner) rootView.findViewById(R.id.allactivities);
        mURL = (EditText) rootView.findViewById(R.id.urltext);
        mAddActivityBtn = (Button) rootView.findViewById(R.id.addactivitybtn);


        mAddActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePoints();

            }
        });

        return rootView;
    }

    public void getUserInfo() {
        mProgressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        database.getReference("allUsers").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userMap = (HashMap<String, String>) dataSnapshot.getValue();

                studyGroupName = userMap.get("studyGroupName");

                List<String> posts = new ArrayList<>(userMap.values());

//                for (String post : posts) {
//                    Log.e("Post Title", post);
//                }

                getActivties();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getActivties() {


        database.getReference(studyGroupName).child("SGActivties").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                activitiesMap = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, NewActivity>>() {
                });

                posts = new ArrayList<>(activitiesMap.values());

                for (NewActivity post : posts) {
                    Log.e("Post Title", post.getActivityName());
                }

                if (getActivity() != null) {
                    adapter = new ArrayAdapter<NewActivity>(getActivity(), android.R.layout.simple_list_item_1, posts);
                    mSpinner.setAdapter(adapter);
                    mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                            selectedActivity = posts.get(position).toString();
                            mURLStr = mURL.getText().toString();
                            Log.e("Selected Activity", selectedActivity);


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }

                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updatePoints() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference(studyGroupName).child("SGActivties").orderByChild("activityName")
                .equalTo(selectedActivity).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // HashMap<String, NewActivity> hashMap1 = (HashMap<String, NewActivity>) dataSnapshot.getValue();

                NewActivity mNewActivity = new NewActivity();
                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                     mNewActivity =  child.getValue(NewActivity.class);

                    //Log.e("mNewActivity",mNewActivity.getActivityName());

                }


                mNewActivity.setActivityURL(mURLStr);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(studyGroupName).child("SGL").child("studentActivities");
                reference.push().setValue(mNewActivity);

                DatabaseReference reference2 = database.getReference(studyGroupName).child("Student").child(userID).child("myActivities");
                reference2.push().setValue(mNewActivity);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
