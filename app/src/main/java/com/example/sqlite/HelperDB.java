package com.example.sqlite;

import static com.example.sqlite.Students.*;
import static com.example.sqlite.Grades.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Helper db for SQLite.
 * @author inbar menahem
 * @version 1.0
 * @since 4th April 2023
 */
public class HelperDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "students.db";
    public static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    /**
     * constructor.
     * @param context the context
     */
    public HelperDB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+ Students.TABLE_STUDENTS;
        strCreate+=" ("+ Students.KEY_PHONE_NUMBER+ "STRING PRIMARY KEY,";
        strCreate+=" "+Students.NAME+" TEXT,";
        strCreate+=" "+Students.ACTIVE+" BOOLEAN,";
        strCreate+=" "+Students.ADDRESS+" TEXT,";
        strCreate+=" "+Students.HOME_NUMBER+" TEXT,";
        strCreate+=" "+Students.PARENTS_NAME+" TEXT,";
        strCreate+=" "+Students.PARENTS_PHONE+" TEXT";
        strCreate+= ") ;";
        db.execSQL(strCreate);

        strCreate = "CREATE TABLE "+Grades.TABLE_GRADES;
        strCreate+=" ("+ Students.KEY_PHONE_NUMBER+ "STRING PRIMARY KEY,";
        strCreate+= " " +Grades.SUBJECT+" TEXT,";
        strCreate+=" "+ Grades.QUARTER+" TEXT,";
        strCreate+=" "+ Grades.GRADE+" INTEGER,";
        strCreate+= ");";
        db.execSQL(strCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        strDelete="DROP TABLE IF EXISTS "+ TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+ TABLE_GRADES;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
