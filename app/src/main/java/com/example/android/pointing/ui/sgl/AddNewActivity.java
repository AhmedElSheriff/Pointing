package com.example.android.pointing.ui.sgl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.pointing.R;
import com.example.android.pointing.db.ActivtiesDBHelper;
import com.example.android.pointing.db.NewActivity;


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
    private int mActivityPointsInt;
    private NewActivity mActivity = new NewActivity();
    private ActivtiesDBHelper myDb;
    private boolean mBool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_new_activity, container, false);

        mActivityName = (EditText) rootView.findViewById(R.id.activityname);
        mActivityPoints = (EditText) rootView.findViewById(R.id.addpoints);
        mAddActivity = (Button) rootView.findViewById(R.id.addnewactivitybtn);


        myDb = new ActivtiesDBHelper(getContext());

        mAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mActivityNameStr = mActivityName.getText().toString();
                mActivityPointsInt = Integer.parseInt(mActivityPoints.getText().toString());

                mActivity.setActivityName(mActivityNameStr);
                mActivity.setActivityPoints(mActivityPointsInt);

                //myDb.deleteAll();
                myDb.insertToDb(mActivity);
            }
        });

        return rootView;
    }

}
