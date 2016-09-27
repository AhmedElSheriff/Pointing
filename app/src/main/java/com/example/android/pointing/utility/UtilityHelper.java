package com.example.android.pointing.utility;

import java.text.SimpleDateFormat;

/**
 * Created by Abshafi on 9/27/2016.
 */
public class UtilityHelper {

    public static String formatTimeStamp(String timestamp)
    {
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = sfd.format(new java.util.Date(Long.parseLong(timestamp)));
        return date;
    }
}
