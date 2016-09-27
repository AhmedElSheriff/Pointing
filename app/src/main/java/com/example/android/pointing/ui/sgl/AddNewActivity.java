package com.example.android.pointing.ui.sgl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.pointing.R;
import com.example.android.pointing.db.NewActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddNewActivity extends Fragment {

    public AddNewActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private EditText mActivityName;
    private EditText mActivityPoints;
    private Button mAddActivity;
    private String mActivityNameStr;
    private Long mActivityPointsLng;
    private NewActivity mActivity = new NewActivity();
    HashMap<String,String> hashMap;
    public String userID;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_new_activity, container, false);


        mActivityName = (EditText) rootView.findViewById(R.id.activityname);
        mActivityPoints = (EditText) rootView.findViewById(R.id.addpoints);
        mAddActivity = (Button) rootView.findViewById(R.id.addnewactivitybtn);

        getUserInfo();

        mAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActivityNameStr = mActivityName.getText().toString();
                mActivityPointsLng = Long.parseLong(mActivityPoints.getText().toString());

                mActivity.setActivityName(mActivityNameStr);
                mActivity.setActivityPoints(mActivityPointsLng);
                mActivity.setActivityStatus("Pending");


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(hashMap.get("studyGroupName")).child("SGActivties");
                reference.push().setValue(mActivity).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "New Activity has been added", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

        return rootView;
    }

    public void getUserInfo()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("allUsers").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                hashMap = (HashMap<String, String>) dataSnapshot.getValue();

                List<String> posts = new ArrayList<>(hashMap.values());

//                for (String post : posts) {
//                    Log.e("Post Title", post);
//                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
