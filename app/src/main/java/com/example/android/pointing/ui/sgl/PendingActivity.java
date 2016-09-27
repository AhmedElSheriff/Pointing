package com.example.android.pointing.ui.sgl;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
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


public class PendingActivity extends Fragment {

    public PendingActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected String studyGroupName;
    protected ListView pendingActivityList;
    protected Firebase ref;
    protected String userID;
    protected ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pending_activity, container, false);

        ref.setAndroidContext(getContext());
        pendingActivityList = (ListView) rootView.findViewById(R.id.pendingactivitylistview);
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

        FirebaseDatabase data = FirebaseDatabase.getInstance();
        data.getReference(studyGroupName).child("Student");
        ref = new Firebase("https://pointings-2264c.firebaseio.com/"+studyGroupName+"/SGL/studentActivities");
        FirebaseListAdapter<NewActivity> firebaseListAdapter = new FirebaseListAdapter<NewActivity>(getActivity(), NewActivity.class, R.layout.pending_activity_single_item, ref) {

            @Override
            protected void populateView(View view, NewActivity s, int i) {

                TextView ActivityName = (TextView) view.findViewById(R.id.text_view_pending_activity_name);
                TextView time = (TextView) view.findViewById(R.id.text_view_pending_edit_time);
                TextView user = (TextView) view.findViewById(R.id.text_view_pending_created_by);
                ActivityName.setText(s.getActivityName());
                time.setText(UtilityHelper.formatTimeStamp(s.getTimestampValue().toString()));
                String createdBy = "Created By " + "<b>" + s.getCreatedBy() + "</b>";
                user.setText(Html.fromHtml(createdBy ));

            }
        };

        mProgressDialog.dismiss();

        pendingActivityList.setAdapter(firebaseListAdapter);
    }

}
