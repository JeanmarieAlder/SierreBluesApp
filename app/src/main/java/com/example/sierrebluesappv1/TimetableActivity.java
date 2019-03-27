package com.example.sierrebluesappv1;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class TimetableActivity extends AppCompatActivity {

    ListView listFriday;
    ListView listSaturday;
    ListView listSunday;
    TextView textFriday;
    TextView textSaturday;
    TextView textSunday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        listFriday = findViewById(R.id.friday_dynamic);
        listSaturday = findViewById(R.id.saturday_dynamic);
        listSunday = findViewById(R.id.sunday_dynamic);
        textFriday = findViewById(R.id.friday_label);
        textSaturday = findViewById(R.id.saturday_label);
        textSunday = findViewById(R.id.sunday_label);

        textFriday.setTypeface(null, Typeface.BOLD);
        listSaturday.setVisibility(View.GONE);
        listSunday.setVisibility(View.GONE);

    }

    public void fridaySelected(View view){
        listFriday.setVisibility(View.VISIBLE);
        listSaturday.setVisibility(View.GONE);
        listSunday.setVisibility(View.GONE);

        textFriday.setTypeface(null, Typeface.BOLD);
        textSaturday.setTypeface(null, Typeface.NORMAL);
        textSunday.setTypeface(null, Typeface.NORMAL);
    }
    public void saturdaySelected(View view){
        listFriday.setVisibility(View.GONE);
        listSaturday.setVisibility(View.VISIBLE);
        listSunday.setVisibility(View.GONE);

        textFriday.setTypeface(null, Typeface.NORMAL);
        textSaturday.setTypeface(null, Typeface.BOLD);
        textFriday.setTypeface(null, Typeface.NORMAL);
    }
    public void sundaySelected(View view){
        listFriday.setVisibility(View.GONE);
        listSaturday.setVisibility(View.GONE);
        listSunday.setVisibility(View.VISIBLE);

        textFriday.setTypeface(null, Typeface.NORMAL);
        textSaturday.setTypeface(null, Typeface.NORMAL);
        textSunday.setTypeface(null, Typeface.BOLD);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent = getIntent();

        switch (item.getItemId())
        {
            case R.id.action_about:
                intent = new Intent(TimetableActivity.this, AboutActivity.class);
                TimetableActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(TimetableActivity.this, SettingsActivity.class);
                TimetableActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
