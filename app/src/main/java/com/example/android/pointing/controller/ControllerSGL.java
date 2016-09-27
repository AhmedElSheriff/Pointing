package com.example.android.pointing.controller;

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

import com.example.android.pointing.MainActivity;
import com.example.android.pointing.R;
import com.example.android.pointing.ui.sgl.AddNewActivity;
import com.example.android.pointing.ui.sgl.PendingActivity;
import com.example.android.pointing.ui.student.LeaderboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ControllerSGL extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_sgl);



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
                    startActivity(new Intent(ControllerSGL.this,MainActivity.class));
                }
            });
        }


        viewPager.setAdapter(new MyAdapter2(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

}
class MyAdapter2 extends FragmentStatePagerAdapter {


    public MyAdapter2(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment = new AddNewActivity();
                break;
            case 1:
                fragment = new PendingActivity();
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
                return "New Activity";
            case 1:
                return "Pendings";
            case 2:
            default:
                return "Leaderboard";
        }
    }
}
