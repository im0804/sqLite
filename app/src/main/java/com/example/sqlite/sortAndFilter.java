package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class sortAndFilter extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String phoneNum;
    ArrayList<String> tbl = new ArrayList<String>();
    String [] helpArr = {"phone number", "name", "address", "parents name", "hone number", "parents phone number", "is active?"}, helpArr2 = {"phone number", "subject", "grade", "quarter"};
    String[] spinnerArrStu = {"as it is", "sort by name", "only name and phone number", "sort by address"}, spinnerArrGra = {"as it is", "sort by subject", "3 grades", "grades by quarter"};
    boolean swSG;
    Spinner spin;
    Switch swUD;
    SQLiteDatabase db;
    Cursor crsr;
    HelperDB hlp;
    Intent gi, si;
    ContentValues cv = new ContentValues();
    ListView lv;
    ArrayAdapter<String> adp;

    String selection = null;
    String[] selectionArgs = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    String limit = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_and_filter);

        swUD = findViewById(R.id.swUD);
        spin = findViewById(R.id.spin);
        lv = findViewById(R.id.lvSAF);
        hlp = new HelperDB(this);

        gi = getIntent();
        swSG = gi.getBooleanExtra("sORg", false);
        phoneNum = gi.getStringExtra("phone");
        cv.put(Students.KEY_PHONE_NUMBER, phoneNum);
        spin.setOnItemSelectedListener(this);

        if (swSG == false){
            adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerArrStu);
            spin.setAdapter(adp);
        }
        else{
            adp = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerArrGra);
            spin.setAdapter(adp);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long rowid) {
        if (pos == 0){
            showTbl();
        } else if (pos == 1) {
            pos1();
        } else if (pos == 2) {
            pos2();
        }
        else{
            pos3();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "please select an option", Toast.LENGTH_LONG).show();
    }

    public void showTbl(){
        if (swSG == false){
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Students.NAME);
            int col3 = crsr.getColumnIndex(Students.ADDRESS);
            int col4 = crsr.getColumnIndex(Students.PARENTS_NAME);
            int col5 = crsr.getColumnIndex(Students.HOME_NUMBER);
            int col6 = crsr.getColumnIndex(Students.PARENTS_PHONE);
            int col7 = crsr.getColumnIndex(Students.ACTIVE);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String name = crsr.getString(col2);
                String add = crsr.getString(col3);
                String parName = crsr.getString(col4);
                String homeNum = crsr.getString(col5);
                String parNum = crsr.getString(col6);
                String isActive = crsr.getString(col7);
                String tmp = "" + key + ", " + name + ", " + add + ", " + parName + ", " + homeNum + ", " + parNum + ", " + isActive;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr);
            lv.setAdapter(customadp);
        }
        else{
            db = hlp.getWritableDatabase();
            crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Grades.GRADE);
            int col3 = crsr.getColumnIndex(Grades.QUARTER);
            int col4 = crsr.getColumnIndex(Grades.SUBJECT);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String gra = crsr.getString(col2);
                String qua = crsr.getString(col3);
                String sub = crsr.getString(col4);
                String tmp = "" + key + ", " + gra + ", " + qua + ", " + sub;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr2);
            lv.setAdapter(customadp);
        }
    }
    public void pos1(){
        String [] columns = null;
        if (swUD.isChecked() == false){
            if (swSG == false)
                orderBy = Students.NAME;
            else
                orderBy = Grades.SUBJECT;
        }
        else{
            if (swSG == false)
                orderBy = Students.NAME +" DESC";
            else
                orderBy = Grades.SUBJECT + " DESC";
        }
        if (swSG == false){
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Students.NAME);
            int col3 = crsr.getColumnIndex(Students.ADDRESS);
            int col4 = crsr.getColumnIndex(Students.PARENTS_NAME);
            int col5 = crsr.getColumnIndex(Students.HOME_NUMBER);
            int col6 = crsr.getColumnIndex(Students.PARENTS_PHONE);
            int col7 = crsr.getColumnIndex(Students.ACTIVE);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String name = crsr.getString(col2);
                String add = crsr.getString(col3);
                String parName = crsr.getString(col4);
                String homeNum = crsr.getString(col5);
                String parNum = crsr.getString(col6);
                String isActive = crsr.getString(col7);
                String tmp = "" + key + ", " + name + ", " + add + ", " + parName + ", " + homeNum + ", " + parNum + ", " + isActive;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr);
            lv.setAdapter(customadp);
        }
        else{
            db = hlp.getWritableDatabase();
            crsr = db.query(Grades.TABLE_GRADES, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Grades.GRADE);
            int col3 = crsr.getColumnIndex(Grades.QUARTER);
            int col4 = crsr.getColumnIndex(Grades.SUBJECT);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String gra = crsr.getString(col2);
                String qua = crsr.getString(col3);
                String sub = crsr.getString(col4);
                String tmp = "" + key + ", " + gra + ", " + qua + ", " + sub;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr2);
            lv.setAdapter(customadp);
        }
    }
    public void pos2(){
        if (swUD.isChecked() == false){
            if (swSG == false) {
                orderBy = Students.NAME;
            }
            else {
                limit = "3";
                orderBy = Grades.SUBJECT;
            }
        }
        else{
            if (swSG == false)
                orderBy = Students.NAME +" DESC";
            else {
                limit = "3";
                orderBy = Grades.GRADE + " DESC";
            }
        }
        if (swSG == false){
            String[] columns = {Students.NAME, Students.KEY_PHONE_NUMBER};
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Students.NAME);
            int col3 = crsr.getColumnIndex(Students.ADDRESS);
            int col4 = crsr.getColumnIndex(Students.PARENTS_NAME);
            int col5 = crsr.getColumnIndex(Students.HOME_NUMBER);
            int col6 = crsr.getColumnIndex(Students.PARENTS_PHONE);
            int col7 = crsr.getColumnIndex(Students.ACTIVE);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String name = crsr.getString(col2);
                String add = crsr.getString(col3);
                String parName = crsr.getString(col4);
                String homeNum = crsr.getString(col5);
                String parNum = crsr.getString(col6);
                String isActive = crsr.getString(col7);
                String tmp = "" + key + ", " + name + ", " + add + ", " + parName + ", " + homeNum + ", " + parNum + ", " + isActive;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr);
            lv.setAdapter(customadp);
        }
        else{
            String[] columns = null;
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Grades.GRADE);
            int col3 = crsr.getColumnIndex(Grades.QUARTER);
            int col4 = crsr.getColumnIndex(Grades.SUBJECT);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String gra = crsr.getString(col2);
                String qua = crsr.getString(col3);
                String sub = crsr.getString(col4);
                String tmp = "" + key + ", " + gra + ", " + qua + ", " + sub;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr2);
            lv.setAdapter(customadp);
        }
    }
    public void pos3(){
        String[] columns = null;
        if (swUD.isChecked() == false){
            if (swSG == false) {
                selection = Students.ADDRESS+ "=?";
                orderBy = Students.NAME;
            }
            else {
                selection = Grades.QUARTER+ "=?";
                orderBy = Grades.GRADE;
            }
        }
        else{
            if (swSG == false) {
                selection = Students.ADDRESS+ "=?";
                orderBy = Students.NAME + " DESC";
            }
            else {
                selection = Grades.QUARTER+ "=?";
                orderBy = Grades.GRADE + " DESC";
            }
        }
        if (swSG == false){
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Students.NAME);
            int col3 = crsr.getColumnIndex(Students.ADDRESS);
            int col4 = crsr.getColumnIndex(Students.PARENTS_NAME);
            int col5 = crsr.getColumnIndex(Students.HOME_NUMBER);
            int col6 = crsr.getColumnIndex(Students.PARENTS_PHONE);
            int col7 = crsr.getColumnIndex(Students.ACTIVE);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String name = crsr.getString(col2);
                String add = crsr.getString(col3);
                String parName = crsr.getString(col4);
                String homeNum = crsr.getString(col5);
                String parNum = crsr.getString(col6);
                String isActive = crsr.getString(col7);
                String tmp = "" + key + ", " + name + ", " + add + ", " + parName + ", " + homeNum + ", " + parNum + ", " + isActive;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr);
            lv.setAdapter(customadp);
        }
        else{
            db = hlp.getWritableDatabase();
            crsr = db.query(Students.TABLE_STUDENTS, columns, selection, selectionArgs, groupBy, having, orderBy);
            int col1 = crsr.getColumnIndex(Students.KEY_PHONE_NUMBER);
            int col2 = crsr.getColumnIndex(Grades.GRADE);
            int col3 = crsr.getColumnIndex(Grades.QUARTER);
            int col4 = crsr.getColumnIndex(Grades.SUBJECT);
            crsr.moveToFirst();
            while (!crsr.isAfterLast()) {
                int key = crsr.getInt(col1);
                String gra = crsr.getString(col2);
                String qua = crsr.getString(col3);
                String sub = crsr.getString(col4);
                String tmp = "" + key + ", " + gra + ", " + qua + ", " + sub;
                tbl.add(tmp);
                crsr.moveToNext();
            }
            crsr.close();
            db.close();
            CustomAdapter customadp = new CustomAdapter(getApplicationContext(), tbl, helpArr2);
            lv.setAdapter(customadp);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.credits) {
            si = new Intent(this, credits.class);
            startActivity(si);
        }
        else if (id == R.id.displayAndDel){
            si = new Intent(this, displaySQLite.class);
            startActivity(si);
        } else if (id == R.id.dataRec) {
            si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}
