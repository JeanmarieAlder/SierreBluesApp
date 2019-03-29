package com.example.sierrebluesappv1.ui.stage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.ui.act.ActEditActivity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;
import com.example.sierrebluesappv1.viewmodel.stage.StageViewModel;

public class StageEditActivity extends AppCompatActivity {

    private String stageId;
    private StageEntity stage;
    private StageViewModel viewModel;
    private boolean editMode;

    private EditText esName;
    private EditText esLocation;
    private EditText esWebsite;
    private EditText esMaxCapacity;
    private Switch swSeatingPlaces;

    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stage);

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

    private void initialize() {
        esName = findViewById(R.id.scene_edit_text_name);
        esLocation = findViewById(R.id.scene_edit_text_address);
        esWebsite = findViewById(R.id.scene_edit_text_website);
        esMaxCapacity = findViewById(R.id.scene_edit_text_max_capacity);
        swSeatingPlaces = findViewById(R.id.edit_stage_switch_seating_places);

        buttonSave = findViewById(R.id.edit_stage_save_button);
        buttonCancel = findViewById(R.id.edit_stage_cancel_button);
        buttonDelete = findViewById(R.id.edit_stage_delete_button);

        buttonSave.setOnClickListener(view -> {
            saveChanges(esName.getText().toString());
        });
        buttonCancel.setOnClickListener(view -> {
            cancelSelected();
        });
        buttonDelete.setOnClickListener(view -> {
            deleteSelected();
        });

        //get act ID from intent and set edit mode to false if new stage
        stageId = getIntent().getStringExtra("stageId");
        editMode = getIntent().getBooleanExtra("isEdit", true);
    }

    private void updateContent() {
        if (stage != null) {
            esName.setText(stage.getName());
            esLocation.setText(stage.getLocation());
            esWebsite.setText(stage.getLocationWebsite());
            esMaxCapacity.setText(String.valueOf(stage.getMaxCapacity()));
            swSeatingPlaces.setChecked(stage.isSeatingPlaces());

        }
    }

    private void saveChanges(String name) {
        if (!("".equals(name)) && name.length() < 30) {
            //Scene name is mandatory and less than 30 characters
            if (editMode) {
                stage.setName(esName.getText().toString());
                stage.setLocation(esLocation.getText().toString());
                stage.setLocationWebsite(esWebsite.getText().toString());
                if(!"".equals(esMaxCapacity.getText().toString())){
                    stage.setMaxCapacity(Integer.parseInt(esMaxCapacity.getText().toString()));
                }else{
                    stage.setMaxCapacity(0);
                }
                stage.setSeatingPlaces(swSeatingPlaces.isChecked());

                viewModel.updateStage(stage, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Update succesful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getApplicationContext(),
                                "Update failed", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                StageEntity newStage = new StageEntity();
                newStage.setName(esName.getText().toString());
                newStage.setLocation(esLocation.getText().toString());
                newStage.setLocationWebsite(esWebsite.getText().toString());
                if(!"".equals(esMaxCapacity.getText().toString())){
                    newStage.setMaxCapacity(Integer.parseInt(esMaxCapacity.getText().toString()));
                }else{
                    newStage.setMaxCapacity(0);
                }
                newStage.setSeatingPlaces(swSeatingPlaces.isChecked());


                viewModel.createStage(newStage, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Creation succesful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(), "Creation failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
            onBackPressed();
        } else {
            //Scene name has not been entered
            Toast.makeText(getApplicationContext(), "Invalid stage name", Toast.LENGTH_LONG).show();
        }

    }

    private void cancelSelected() {
        onBackPressed();
    }

    private void deleteSelected() {
        if(editMode){
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
        }
        onBackPressed();
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
                intent = new Intent(StageEditActivity.this, AboutActivity.class);
                StageEditActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(StageEditActivity.this, SettingsActivity.class);
                StageEditActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
