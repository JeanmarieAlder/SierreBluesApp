package com.example.sierrebluesappv1.ui.stage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.adapter.RecyclerAdapter;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.ui.act.ActDetailActivity;
import com.example.sierrebluesappv1.ui.act.ActEditActivity;
import com.example.sierrebluesappv1.ui.act.ActsActivity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.util.RecyclerViewItemClickListener;
import com.example.sierrebluesappv1.viewmodel.act.ActsListViewModel;
import com.example.sierrebluesappv1.viewmodel.stage.StagesListViewModel;

import java.util.ArrayList;
import java.util.List;

public class StagesActivity extends AppCompatActivity {

    private RecyclerAdapter<StageEntity> adapter;
    private List<StageEntity> stages;
    private StagesListViewModel listViewModel;
    private RecyclerView rView;

    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages);
        Toast.makeText(this, getString(R.string.message_delete_edit), Toast.LENGTH_LONG).show();

        addButton = findViewById(R.id.add_scene_button);
        addButton.setOnClickListener(view -> {
            addSelected();
        });

        rView = (RecyclerView)findViewById(R.id.recycler_view_stages);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);

        stages = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(StagesActivity.this, StageDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("stageId", stages.get(position).getName());
                startActivity(intent);
            }
        });
        StagesListViewModel.Factory factory = new StagesListViewModel.Factory(
                getApplication());

        listViewModel = ViewModelProviders.of(this, factory).get(StagesListViewModel.class);
        listViewModel.getAllStages().observe(this, stageEntities -> {
            if (stageEntities != null) {
                stages = stageEntities;
                adapter.setData(stages);
            }
        });

        rView.setAdapter(adapter);
    }

    private void addSelected() {
        Intent intent = new Intent(StagesActivity.this, StageEditActivity.class);
        intent.putExtra("isEdit", false);
        StagesActivity.this.startActivity(intent);
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
                intent = new Intent(StagesActivity.this, AboutActivity.class);
                StagesActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(StagesActivity.this, SettingsActivity.class);
                StagesActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = RecyclerAdapter.getPos();
        switch(item.getItemId()) {
            case 1:
                //new edit window
                Intent intent = new Intent(StagesActivity.this, StageEditActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("stageId", stages.get(position).getName());
                startActivity(intent);

                return true;
            case 2:
                //delete item
                listViewModel.deleteStage(stages.get(position), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),
                                "Stage deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Error, couldn't delete the stage", Toast.LENGTH_LONG).show();
                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
