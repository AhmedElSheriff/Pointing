//package com.example.android.pointing.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//
///**
// * Created by Abshafi on 9/19/2016.
// */
//public class ActivtiesDBHelper extends SQLiteOpenHelper {
//
//    public static final String DATABASE_NAME = "Addnewactivity.db";
//    public static final String TABLE_NAME = "newactivity_table";
//   // public static final String COLUMN_ID = "id";
//    public static final String ACTIVITY_NAME = "activity_name";
//    public static final String ACTIVITY_POINTS = "activity_points";
//
//
//
//    public ActivtiesDBHelper(Context context)
//    {
//        super(context, DATABASE_NAME, null, 4);
//
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
////        db.execSQL("create table " + TABLE_NAME + " (" + COLUMN_ID + " integer primary key autoincrement, "
////                + ACTIVITY_NAME + " text not null, " + ACTIVITY_POINTS + " integer not null);");
//
//        db.execSQL("create table " + TABLE_NAME + " ("
//                + ACTIVITY_NAME + " text primary key not null, " + ACTIVITY_POINTS + " integer not null);");
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//    }
//
//    public void insertToDb(NewActivity newActivity)
//    {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        //if(checkIfExists(newActivity.getActivityName()) == false)
//        //{
//            contentValues.put(ACTIVITY_NAME,newActivity.getActivityName());
//            contentValues.put(ACTIVITY_POINTS, newActivity.getActivityPoints());
//
//            db.insert(TABLE_NAME,null,contentValues);
//            //return true;
//        //}
//
////        else
////
////            return false;
//
//    }
//
//    public ArrayList<NewActivity> getAllData()
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<NewActivity> arrayList = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME , null);
//        cursor.moveToNext();
//        while (cursor.isAfterLast() == false)
//        {
//            NewActivity newActivity = new NewActivity();
//            newActivity.setActivityName(cursor.getString(0));
//            newActivity.setActivityPoints(cursor.getInt(1));
//
//            arrayList.add(newActivity);
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//        return arrayList;
//    }
//
//    public void deleteAll()
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME,null,null);
//    }
//
//    private boolean checkIfExists(String activityName)
//    {
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String check = "select * from " + TABLE_NAME + " where " + ACTIVITY_NAME + " = " + activityName;
//        Cursor cursor = db.rawQuery(check,null);
//        if(cursor.getCount() <= 0)
//        {
//            cursor.close();
//            return false;
//        }
//
//        cursor.close();
//        return true;
//
//    }
//}
