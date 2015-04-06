package com.example.softwareengineering.softwareengineering;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class solutionTypes extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_types);

        String[] solutionTypes = {"Solution", "Dilution", "Serial Dilution", "External Standards", "Internal Standards"};
        ListAdapter soluAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solutionTypes);

        ListView soluTypes = (ListView) findViewById(R.id.soluTypes);

        soluTypes.setAdapter(soluAdapter);

        soluTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tvShowPicked = "You selected " + String.valueOf(parent.getItemAtPosition(position));

                Intent nextScreen = new Intent(solutionTypes.this, solutionQuestions.class);
                startActivity(nextScreen);
                Toast.makeText(solutionTypes.this, tvShowPicked, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_solution_types, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
