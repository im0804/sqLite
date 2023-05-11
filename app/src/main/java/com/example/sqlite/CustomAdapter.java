package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> sqlList;
    String subName[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<String> sqlList, String[] subName) {
        this.context = applicationContext;
        this.sqlList = sqlList;
        this.subName = subName;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return sqlList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_lv_layout, null);
        TextView sql = (TextView) view.findViewById(R.id.tvMain);
        TextView sub = (TextView) view.findViewById(R.id.tvSub);
        sql.setText(sqlList.indexOf(i));
        sql.setText(subName[i]);
        return view;
    }
}
