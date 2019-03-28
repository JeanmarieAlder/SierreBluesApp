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

    private static final String TAG = "ActsActivity";

    private RecyclerAdapter<ActEntity> adapter;
    private List<ActEntity> acts;
    private ActsListViewModel listViewModel;
    private RecyclerView rView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acts);
        //getLayoutInflater().inflate(R.layout.activity_acts, frameLayout);
        Toast.makeText(this, getString(R.string.message_delete_edit), Toast.LENGTH_LONG).show();

        rView = (RecyclerView)findViewById(R.id.recycler_view_acts);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rView.getContext(),
                LinearLayoutManager.VERTICAL);
        rView.addItemDecoration(dividerItemDecoration);
        registerForContextMenu(rView);

        acts = new ArrayList<>();
        adapter = new RecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + acts.get(position).getArtistName());

                Intent intent = new Intent(ActsActivity.this, ActDetailActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("actId", acts.get(position).getIdAct());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + acts.get(position).getArtistName());


                //createDeleteDialog(position);
            }
        });

        ActsListViewModel.Factory factory = new ActsListViewModel.Factory(
                getApplication());

        listViewModel = ViewModelProviders.of(this, factory).get(ActsListViewModel.class);
        listViewModel.getAllActs().observe(this, accountEntities -> {
            if (accountEntities != null) {
                acts = accountEntities;
                adapter.setData(acts);
            }
        });

        rView.setAdapter(adapter);
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

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
