package com.example.android.pointing.ui.student;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.android.pointing.R;
import com.example.android.pointing.db.ActivtiesDBHelper;
import com.example.android.pointing.db.NewActivity;

import java.util.ArrayList;


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
    private String mURLStr;
    private ActivtiesDBHelper myDb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_addactivity, container, false);

        myDb = new ActivtiesDBHelper(getContext());
        mSpinner = (Spinner) rootView.findViewById(R.id.allactivities);
        mURL = (EditText) rootView.findViewById(R.id.urltext);
        mAddActivityBtn = (Button) rootView.findViewById(R.id.addactivitybtn);

        ArrayList<NewActivity> arrayList;
        arrayList = myDb.getAllData();
        ArrayAdapter<NewActivity> adapter = new ArrayAdapter<NewActivity>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        mSpinner.setAdapter(adapter);

        mAddActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mURLStr = mURL.getText().toString();

            }
        });

        return rootView;
    }

}
