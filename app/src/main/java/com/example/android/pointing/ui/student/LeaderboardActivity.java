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
import com.example.android.pointing.login.User;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LeaderboardActivity extends Fragment {


    public LeaderboardActivity() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String studyGroupName;
    private String points;
    ListView leaderBoardList;
    public Firebase ref;
    protected ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        ref.setAndroidContext(getContext());
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Please Wait!");

        leaderBoardList = (ListView) rootView.findViewById(R.id.leaderboardlistview);

        getUserInfo();

        return rootView;
    }


    public void getUserInfo() {
        mProgressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("allUsers").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, String> hashMap = (HashMap<String, String>) dataSnapshot.getValue();

                List<String> posts = new ArrayList<>(hashMap.values());

                studyGroupName = hashMap.get("studyGroupName");

//                for (String post : posts) {
//                    Log.e("Post Title", post);
//                }

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
         ref = new Firebase("https://pointings-2264c.firebaseio.com/" + studyGroupName + "/Student");
        FirebaseListAdapter<User> firebaseListAdapter = new FirebaseListAdapter<User>(getActivity(), User.class, R.layout.leaderboardsinglerow, ref) {

            @Override
            protected void populateView(View view, User user, int i) {

                TextView userName = (TextView) view.findViewById(R.id.usernametextview);
                TextView userPoints = (TextView) view.findViewById(R.id.pointstextview);
                userName.setText(user.getUsername());
                userPoints.setText(Long.toString(user.getUserPoints()));

            }
        };

        mProgressDialog.dismiss();

        leaderBoardList.setAdapter(firebaseListAdapter);
    }


}
