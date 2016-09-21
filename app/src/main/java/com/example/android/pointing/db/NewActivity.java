package com.example.android.pointing.db;

/**
 * Created by Abshafi on 9/19/2016.
 */
public class NewActivity {

    String activityName;
    int activityPoints;

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityPoints(int activityPoints) {
        this.activityPoints = activityPoints;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getActivityPoints() {
        return activityPoints;
    }

    @Override
    public String toString() {
        return activityName;
    }
}
