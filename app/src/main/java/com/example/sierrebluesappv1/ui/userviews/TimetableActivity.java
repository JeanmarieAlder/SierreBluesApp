package com.example.sierrebluesappv1.ui.userviews;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sierrebluesappv1.ui.nav.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.ui.settings.SettingsActivity;
import com.example.sierrebluesappv1.adapter.RecyclerAdapter;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.ui.act.ActDetailActivity;
import com.example.sierrebluesappv1.util.RecyclerViewItemClickListener;
import com.example.sierrebluesappv1.viewmodel.act.ActsListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TimetableActivity extends AppCompatActivity {

    private RecyclerAdapter<ActEntity> adapterFriday;
    private RecyclerAdapter<ActEntity> adapterSaturday;
    private RecyclerAdapter<ActEntity> adapterSunday;

    private List<ActEntity> actsFriday;
    private List<ActEntity> actsSaturday;
    private List<ActEntity> actsSunday;

    private ActsListViewModel listFridayViewModel;
    private RecyclerView listFriday;
    private ActsListViewModel listSaturdayViewModel;
    private RecyclerView listSaturday;
    private ActsListViewModel listSundayViewModel;
    private RecyclerView listSunday;
    private TextView textFriday;
    private TextView textSaturday;
    private TextView textSunday;

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

        initializeViewModels();

        textFriday.setTypeface(null, Typeface.BOLD);
        listSaturday.setVisibility(View.GONE);
        listSunday.setVisibility(View.GONE);

    }

    /**
     *  Initializes views for friday, saturday and sunday.     *
     */
    private void initializeViewModels(){

        //Friday scedule retrieved
        listFriday.setLayoutManager(new LinearLayoutManager(this));
        actsFriday = new ArrayList<>();
        adapterFriday = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(TimetableActivity.this, ActDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", actsFriday.get(position).getIdAct());
                intent.putExtra("isUser", true);
                startActivity(intent);
            }
        });
        ActsListViewModel.Factory factory = new ActsListViewModel.Factory(
                getApplication());
        listFridayViewModel = ViewModelProviders.of(this, factory).get(ActsListViewModel.class);
        listFridayViewModel.getActsByDay("Friday").observe(this, actEntities -> {
            if (actEntities != null) {
                actsFriday = actEntities;
                adapterFriday.setData(actsFriday);
            }
        });
        listFriday.setAdapter(adapterFriday);

        //Saturday scedule retrieved
        listSaturday.setLayoutManager(new LinearLayoutManager(this));
        actsSaturday = new ArrayList<>();
        adapterSaturday = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(TimetableActivity.this, ActDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", actsSaturday.get(position).getIdAct());
                intent.putExtra("isUser", true);
                startActivity(intent);
            }
        });
        factory = new ActsListViewModel.Factory(
                getApplication());
        listFridayViewModel = ViewModelProviders.of(this, factory).get(ActsListViewModel.class);
        listFridayViewModel.getActsByDay("Saturday").observe(this, actEntities -> {
            if (actEntities != null) {
                actsSaturday = actEntities;
                adapterSaturday.setData(actsSaturday);
            }
        });
        listSaturday.setAdapter(adapterSaturday);

        //Sunday scedule retrieved
        listSunday.setLayoutManager(new LinearLayoutManager(this));
        actsSunday = new ArrayList<>();
        adapterSunday = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(TimetableActivity.this, ActDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", actsSunday.get(position).getIdAct());
                intent.putExtra("isUser", true);
                startActivity(intent);
            }
        });
        factory = new ActsListViewModel.Factory(
                getApplication());
        listFridayViewModel = ViewModelProviders.of(this, factory).get(ActsListViewModel.class);
        listFridayViewModel.getActsByDay("Sunday").observe(this, actEntities -> {
            if (actEntities != null) {
                actsSunday = actEntities;
                adapterSunday.setData(actsSunday);
            }
        });
        listSunday.setAdapter(adapterSunday);
    }

    /**
     * Shows friday timetable and hides others
     * @param view
     */
    public void fridaySelected(View view){
        listFriday.setVisibility(View.VISIBLE);
        listSaturday.setVisibility(View.GONE);
        listSunday.setVisibility(View.GONE);

        textFriday.setTypeface(null, Typeface.BOLD);
        textSaturday.setTypeface(null, Typeface.NORMAL);
        textSunday.setTypeface(null, Typeface.NORMAL);
    }
    /**
     * Shows Saturday timetable and hides others
     * @param view
     */
    public void saturdaySelected(View view){
        listFriday.setVisibility(View.GONE);
        listSaturday.setVisibility(View.VISIBLE);
        listSunday.setVisibility(View.GONE);

        textFriday.setTypeface(null, Typeface.NORMAL);
        textSaturday.setTypeface(null, Typeface.BOLD);
        textFriday.setTypeface(null, Typeface.NORMAL);
    }
    /**
     * Shows Sunday timetable and hides others
     * @param view
     */
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
