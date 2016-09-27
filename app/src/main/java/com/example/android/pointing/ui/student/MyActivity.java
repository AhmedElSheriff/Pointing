package com.example.android.pointing.ui.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.pointing.R;
import com.example.android.pointing.db.NewActivity;
import com.example.android.pointing.utility.UtilityHelper;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MyActivity extends Fragment {
    Firebase ref;
    private String studyGroupName;
    ListView mMyActivityListView;
    String userID;
    int position;
    protected ProgressDialog mProgressDialog;

    public MyActivity() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=  inflater.inflate(R.layout.fragment_myactivity, container, false);
        mMyActivityListView = (ListView) rootView.findViewById(R.id.my_activity_list_view);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Please Wait!");
        getUserInfo();
        return rootView;
    }

    public void getUserInfo() {
        mProgressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("allUsers").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> hashMap = (HashMap<String, String>) dataSnapshot.getValue();
                studyGroupName = hashMap.get("studyGroupName");
                getFirebaseUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getFirebaseUI() {
        ref = new Firebase("https://pointings-2264c.firebaseio.com/"+studyGroupName+"/Student/"+userID+"/myActivities");
        FirebaseListAdapter<NewActivity> firebaseListAdapter = new FirebaseListAdapter<NewActivity>(getActivity(), NewActivity.class, R.layout.my_activity_single_item, ref) {

            @Override
            protected void populateView(View view, NewActivity s, int i) {
                TextView ActivityName = (TextView) view.findViewById(R.id.text_view_activity_name);
                TextView ActivityStatus = (TextView) view.findViewById(R.id.text_view_activity_status);
                TextView time = (TextView) view.findViewById(R.id.text_view_edit_time);
                ActivityName.setText(s.getActivityName());
                ActivityStatus.setText(s.getActivityStatus());
                time.setText(UtilityHelper.formatTimeStamp(s.getTimestampValue().toString()));
            }
        };
        mProgressDialog.dismiss();
        mMyActivityListView.setAdapter(firebaseListAdapter);
    }


}

