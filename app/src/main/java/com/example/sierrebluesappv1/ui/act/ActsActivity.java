package com.example.sierrebluesappv1.ui.act;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.adapter.RecyclerAdapter;
import com.example.sierrebluesappv1.database.AppDatabase;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.ui.BaseActivity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.util.RecyclerViewItemClickListener;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;
import com.example.sierrebluesappv1.viewmodel.act.ActsListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.sierrebluesappv1.database.AppDatabase.initializeDemoData;

public class ActsActivity extends AppCompatActivity {


    private RecyclerAdapter<ActEntity> adapter;
    private List<ActEntity> acts;
    private ActsListViewModel listViewModel;
    private RecyclerView rView;

    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acts);
        Toast.makeText(this, getString(R.string.message_delete_edit), Toast.LENGTH_LONG).show();

        addButton = findViewById(R.id.add_acts_button);
        addButton.setOnClickListener(view -> {
            addSelected();
        });

        rView = (RecyclerView)findViewById(R.id.recycler_view_acts);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);


        acts = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ActsActivity.this, ActDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", acts.get(position).getIdAct());
                startActivity(intent);
            }
        });

        ActsListViewModel.Factory factory = new ActsListViewModel.Factory(
                getApplication());

        listViewModel = ViewModelProviders.of(this, factory).get(ActsListViewModel.class);
        listViewModel.getAllActs().observe(this, actEntities -> {
            if (actEntities != null) {
                acts = actEntities;
                adapter.setData(acts);
            }
        });

        rView.setAdapter(adapter);
    }

    private void addSelected() {
        Intent intent = new Intent(ActsActivity.this, ActEditActivity.class);
        intent.putExtra("isEdit", false);
        ActsActivity.this.startActivity(intent);
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
                intent = new Intent(ActsActivity.this, AboutActivity.class);
                ActsActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(ActsActivity.this, SettingsActivity.class);
                ActsActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = RecyclerAdapter.getPos();
        switch(item.getItemId()) {
            case 1:
                //new edit window
                Intent intent = new Intent(ActsActivity.this, ActEditActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", acts.get(position).getIdAct());
                startActivity(intent);

                return true;
            case 2:
                //delete item
                listViewModel.deleteAct(acts.get(position), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),
                                "Act deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Error, couldn't delete the act", Toast.LENGTH_LONG).show();
                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
