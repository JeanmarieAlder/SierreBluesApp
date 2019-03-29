package com.example.sierrebluesappv1.ui.stage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.stage.StageViewModel;

public class StageDetailActivity extends AppCompatActivity {

    private Button buttonEdit;
    private Button buttonDelete;
    private Button buttonCancel;

    private TextView tvName;
    private TextView tvLocation;
    private TextView tvWebsite;
    private TextView tvMaxCapacity;
    private TextView tvSeatingPlaces;

    StageEntity stage;
    private StageViewModel viewModel;
    private String stageId;
    private boolean isUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);

        initialize();

        StageViewModel.Factory factory = new StageViewModel.Factory(
                getApplication(), stageId);
        viewModel = ViewModelProviders.of(this, factory).get(StageViewModel.class);
        viewModel.getStage().observe(this, stageEntity -> {
            if (stageEntity != null) {
                stage = stageEntity;
                updateContent();
            }
        });
    }

    private void updateContent() {
        if (stage != null) {
            tvName.setText(stage.getName());
            tvLocation.setText(stage.getLocation());
            tvWebsite.setText(stage.getLocationWebsite());
            tvMaxCapacity.setText(String.valueOf(stage.getMaxCapacity()));
            if(stage.isSeatingPlaces()){
                tvSeatingPlaces.setText("Yes");
            }else{
                tvSeatingPlaces.setText("No");
            }


        }
    }

    private void initialize() {
        buttonEdit = findViewById(R.id.detail_stage_edit_button);
        buttonDelete = findViewById(R.id.detail_stage_delete_button);
        buttonCancel = findViewById(R.id.detail_stage_cancel_button);

        isUser = getIntent().getBooleanExtra("isUser", false);

        if(isUser){
            buttonDelete.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
        }else{
            buttonEdit.setOnClickListener(view -> edit(stage.getName()));

            buttonDelete.setOnClickListener(view -> deleteSelected());
        }

        buttonCancel.setOnClickListener(view -> onBackPressed());

        tvName = findViewById(R.id.stage_detail_text_name);
        tvLocation = findViewById(R.id.stage_detail_text_address);
        tvWebsite = findViewById(R.id.stage_detail_text_website);
        tvMaxCapacity = findViewById(R.id.stage_detail_text_capacity);
        tvSeatingPlaces = findViewById(R.id.stage_detail_text_seating);

        //get act ID from intent and set edit mode to false if new stage
        stageId = getIntent().getStringExtra("stageId");
    }

    private void deleteSelected() {
        viewModel.deleteStage(stage, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Stage deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Error, couldn't delete the stage", Toast.LENGTH_LONG).show();
            }
        });
        onBackPressed();
    }

    private void edit(String name) {
        Intent intent = new Intent(StageDetailActivity.this, StageEditActivity.class);
        intent.putExtra("stageId", name);
        startActivity(intent);
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
                intent = new Intent(StageDetailActivity.this, AboutActivity.class);
                StageDetailActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(StageDetailActivity.this, SettingsActivity.class);
                StageDetailActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
