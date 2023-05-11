package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Main activity.
 * @author inbar menahem
 * @version 1.0
 * @since 4th April 2023
 */
public class MainActivity extends AppCompatActivity {
    EditText etName, etPN, etAddress, etHPN, etPName, etPPN, etGrade, etSubject, etQuarter;
    Switch swActive, swSG;
    Intent si;
    ContentValues cv = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etPN = findViewById(R.id.etPN);
        etAddress = findViewById(R.id.etAddress);
        etHPN = findViewById(R.id.etHPN);
        etPName = findViewById(R.id.etPName);
        etPPN = findViewById(R.id.etPPN);
        etSubject = findViewById(R.id.etSubject);
        etGrade = findViewById(R.id.etGrade);
        etQuarter = findViewById(R.id.etQuarter);
        swActive = findViewById(R.id.swActive);
        swSG = findViewById(R.id.swSG);

        etName.setHint("student name");
        etPN.setHint("phone number");
        etAddress.setHint("address");
        etHPN.setHint("home phone number");
        etPName.setHint("parents name");
        etPPN.setHint("parent phone number");
        etGrade.setHint("Student's grade");
        etQuarter.setHint("what quarter");
        etSubject.setHint("subject");
    }

    /**
     * Update.
     * updates the parameters for the table and moves to an other activity
     * @param view the view
     */
    public void update(View view) {
        cv.put(Students.KEY_PHONE_NUMBER, etPN.getText().toString());
        if (swSG.isChecked() == false) {
            cv.put(Students.NAME, etName.getText().toString());
            cv.put(Students.ADDRESS, etAddress.getText().toString());
            cv.put(Students.HOME_NUMBER, etHPN.getText().toString());
            cv.put(Students.PARENTS_NAME, etPN.getText().toString());
            cv.put(Students.PARENTS_PHONE, etPPN.getText().toString());
            cv.put(Students.ACTIVE, String.valueOf(swActive));
        }
        else {
            cv.put(Grades.GRADE, etGrade.getText().toString());
            cv.put(Grades.SUBJECT, etSubject.getText().toString());
            cv.put(Grades.QUARTER, etQuarter.getText().toString());
        }
        si = new Intent(this, displaySQLite.class);
        si.putExtra("sORg", swSG.isChecked());
        startActivity(si);
    }

    /**
     * Delete.
     * clear the texts the user typed
     * @param view the view
     */
    public void delete(View view) {
        if (swSG.isChecked()){
            etName.setText("");
            etPN.setText("");
            etAddress.setText("");
            etHPN.setText("");
            etPName.setText("");
            etPPN.setText("");
            swActive.setChecked(false);
        }
        else {
            etQuarter.setText("");
            etSubject.setText("");
            etGrade.setText("");
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
        } else if (id == R.id.sortAndFilter) {
            si = new Intent(this, sortAndFilter.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}