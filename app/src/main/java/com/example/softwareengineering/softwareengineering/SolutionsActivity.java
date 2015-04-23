package com.example.softwareengineering.softwareengineering;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import database.SolutionDBHelper;


public class SolutionsActivity extends Activity {
    private SolutionDBHelper mDbHelper = new SolutionDBHelper(this);
    boolean file;
    int id;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/DK Cool Crayon.ttf");
        TextView text = (TextView) findViewById(R.id.show);
        text.setTypeface(myTypeface);

        Bundle type = getIntent().getExtras();

        if(type != null) {
            this.id = type.getInt("id");
            this.file = type.getBoolean("file");
        }

        String[] names = mDbHelper.getSolutionNames();

        ListAdapter soluAdapter = new typeAdapter(this, names);

        ListView soluTypes = (ListView) findViewById(R.id.solutions);

        soluTypes.setAdapter(soluAdapter);

        soluTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data = mDbHelper.getSolutionData(position);
                Intent nextScreen = new Intent(SolutionsActivity.this, QuestionsActivity.class);
                nextScreen.putExtra("id", id);
                nextScreen.putExtra("file", true);
                nextScreen.putExtra("data", data);
                startActivity(nextScreen);
            }
        });
    }
}