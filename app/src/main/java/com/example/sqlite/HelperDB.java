package com.example.sqlite;

import static com.example.sqlite.Students.*;
import static com.example.sqlite.Grades.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HelperDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    public static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+ TABLE_STUDENTS;
        strCreate+=" ("+ PHONE_NUMBER+ "STRING PRIMARY KEY,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+ACTIVE+" BOOLEAN,";
        strCreate+=" "+ADDRESS+" TEXT,";
        strCreate+=" "+HOME_NUMBER+" TEXT,";
        strCreate+=" "+PARENTS_NAME+" TEXT,";
        strCreate+=" "+PARENTS_PHONE+" TEXT";
        strCreate+= ") ;";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        strDelete="DROP TABLE IF EXISTS "+ TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+ TABLE_GRADES;

        onCreate(db);
    }
}
