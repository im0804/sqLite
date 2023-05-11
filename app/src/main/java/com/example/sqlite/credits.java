package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class credits extends AppCompatActivity {

    TextView tvCredits;
    Intent si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        tvCredits = (TextView) findViewById(R.id.tvCredits);
        tvCredits.setText("albert i did this project alone so you should be proud of me!!"+'\n');
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sortAndFilter) {
            si = new Intent(this, sortAndFilter.class);
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