package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class displaySQLite extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String phoneNum;
    String[] helpArr = {"phone number", "name", "address", "parents name", "home number", "parents phone number", "is active?"};
    String[] helpArr2 = {"phone number", "subject", "grade", "quarter"};
    boolean swSG;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    Intent gi, si;
    ListView lv;
    ArrayList<String> tbl = new ArrayList<String>();
    TextView tvMain, tvSub;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sqlite);

        tvMain = (TextView) findViewById(R.id.tvmain);
        tvSub = (TextView) findViewById(R.id.tvsub);
        lv = findViewById(R.id.lvSAF);
        hlp = new HelperDB(this);

        gi = getIntent();
        swSG = gi.getBooleanExtra("sORg", false);

        hlp = new HelperDB(this);
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
            crsr = db.query(Students.TABLE_STUDENTS, null, null, null, null, null, null);
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
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        tvMain.setText(tbl.indexOf(position)+":");
        tvSub.setText(helpArr[position]);
        pos = position;
    }

    public void delete(View view) {
        if (tvMain.getText().toString().equals("")){
            Toast.makeText(this, "please select a row to delete", Toast.LENGTH_LONG).show();
        }
        else {
            if (swSG == false) {
                db = hlp.getWritableDatabase();
                db.delete(Students.TABLE_STUDENTS, phoneNum + "=?", new String[]{Integer.toString(pos + 1)});
                db.close();
            }
            else {
                db = hlp.getWritableDatabase();
                db.delete(Grades.TABLE_GRADES, phoneNum + "=?", new String[]{Integer.toString(pos + 1)});
                db.close();
            }
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
        else if (id == R.id.sortAndFilter){
            si = new Intent(this, sortAndFilter.class);
            startActivity(si);
        } else if (id == R.id.dataRec) {
            si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}