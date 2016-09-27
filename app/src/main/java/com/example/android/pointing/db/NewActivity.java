package com.example.android.pointing.db;

/**
 * Created by Abshafi on 9/19/2016.
 */
public class NewActivity {

    public String activityName;
    public long activityPoints;
    public String activityURL;
    public String activityStatus;
    public Object timestampValue;
    public String createdBy;

    public Object getTimestampValue() {
        return timestampValue;
    }

    public void setTimestampValue(Object object) {
        this.timestampValue = object;

    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public String getCreatedBy() {
        return createdBy;
    }
//    public Object getTimeStampValue() {
//        //SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//
////        return  sfd.format(new Date(Long.parseLong(timestampValue.toString())));
//        return timestampValue;
//    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityURL(String activityURL) {
        this.activityURL = activityURL;
    }

    public String getActivityURL() {
        return activityURL;
    }


    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityPoints(long activityPoints) {
        this.activityPoints = activityPoints;
    }

    public String getActivityName() {
        return activityName;
    }

    public long getActivityPoints() {
        return activityPoints;
    }


    @Override
    public String toString() {
        return activityName;
    }
}
