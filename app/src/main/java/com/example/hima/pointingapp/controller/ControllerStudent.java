package com.example.hima.pointingapp.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hima.pointingapp.MainActivity;
import com.example.hima.pointingapp.R;
import com.example.hima.pointingapp.ui.student.AddActivity;
import com.example.hima.pointingapp.ui.student.LeaderboardActivity;
import com.example.hima.pointingapp.ui.student.MyActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ControllerStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_student);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setTitleTextColor(0xFFFFFFFF);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(ControllerStudent.this,MainActivity.class));
                }
            });
        }


        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        }
}
class MyAdapter extends FragmentStatePagerAdapter{


    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment=null;
        switch (position){
            case 0:
                fragment = new AddActivity();
                break;
            case 1:
                fragment = new MyActivity();
                break;
            case 2:
                fragment = new LeaderboardActivity();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Add Activity";
            case 1:
                 return "My Activities";
            case 2:
            default:
                return "Leaderboard";
        }
    }
}
