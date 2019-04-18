package com.example.sierrebluesappv1.ui.stage;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.sierrebluesappv1.ui.nav.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.ui.settings.SettingsActivity;
import com.example.sierrebluesappv1.database.entity.StageEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.stage.StageViewModel;

public class StageEditActivity extends AppCompatActivity {

    private String stageId;
    private String initialStageId;
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

        stage = new StageEntity();

        //Initializes buttons, views, current ID and edit mode
        initialize();

        //Creates ViewModel
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

    /**
     * Initializes views, buttons, id and editmode
     */
    private void initialize() {
        esName = findViewById(R.id.scene_edit_text_name);
        esLocation = findViewById(R.id.scene_edit_text_address);
        esWebsite = findViewById(R.id.scene_edit_text_website);
        esMaxCapacity = findViewById(R.id.scene_edit_text_max_capacity);
        swSeatingPlaces = findViewById(R.id.edit_stage_switch_seating_places);

        buttonSave = findViewById(R.id.edit_stage_save_button);
        buttonCancel = findViewById(R.id.edit_stage_cancel_button);
        buttonDelete = findViewById(R.id.edit_stage_delete_button);

        buttonSave.setOnClickListener(view -> saveChanges(esName.getText().toString()));
        buttonCancel.setOnClickListener(view -> cancelSelected());
        buttonDelete.setOnClickListener(view -> deleteSelected());

        //get act ID from intent and set edit mode to false if new stage
        stageId = getIntent().getStringExtra("stageId");
        initialStageId = stageId;
        editMode = getIntent().getBooleanExtra("isEdit", true);
    }

    /**
     * Updates UI content if changes in DB occure
     */
    private void updateContent() {
        if (stage != null) {
            esName.setText(stageId);
            esLocation.setText(stage.getAddress());
            esWebsite.setText(stage.getWebsite());
            esMaxCapacity.setText(String.valueOf(stage.getMaxCapacity()));
            swSeatingPlaces.setChecked(stage.isSeatingPlaces());

        }
    }

    /**
     * Method when save button is used, will check if stage is new
     * or is being edited.
     * @param name ID of the stage to save
     */
    private void saveChanges(String name) {
        //checks if name (id) has changed
        if(!(name.equals(initialStageId)) && editMode){
            final AlertDialog alertDialogStageEdit = new AlertDialog.Builder(this).create();
            alertDialogStageEdit.setTitle(getString(R.string.alert_edit_stage));
            alertDialogStageEdit.setCancelable(false);
            alertDialogStageEdit.setMessage(getString(R.string.alert_stage_edit_text));
            alertDialogStageEdit.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_save_copy), (dialog, which) ->{
                Toast.makeText(this, "Saved a copy", Toast.LENGTH_LONG).show();
                performSave(name);
            });
            alertDialogStageEdit.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel),
                    (dialog, which) ->{
                        alertDialogStageEdit.dismiss();
                        return;
                    } );
            alertDialogStageEdit.show();
        }else{
            performSave(name);
        }



    }

    private void performSave(String name)
    {
        if (!("".equals(name)) && name.length() < 30) {
            //Scene name is mandatory and less than 30 characters
            if (editMode) {
                //Edit mode part
                stage.setName(esName.getText().toString());
                stage.setAddress(esLocation.getText().toString());
                stage.setWebsite(esWebsite.getText().toString());
                //If max capacity is not filled, autofill it with 0
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
                //New stage part
                StageEntity newStage = new StageEntity();
                newStage.setName(esName.getText().toString());
                newStage.setAddress(esLocation.getText().toString());
                newStage.setWebsite(esWebsite.getText().toString());
                //If max capacity is not filled, autofill it with 0
                if(!"".equals(esMaxCapacity.getText().toString())){
                    newStage.setMaxCapacity(Integer.parseInt(esMaxCapacity.getText().toString()));
                }else{
                    newStage.setMaxCapacity(0);
                }
                newStage.setSeatingPlaces(swSeatingPlaces.isChecked());

                viewModel.createStage(newStage, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),
                                "Creation succesful", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(getApplicationContext(),
                                "Creation failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
            onBackPressed(); //go back to previous activity
        } else {
            //Scene name has not been entered
            Toast.makeText(getApplicationContext(),
                    "Invalid stage name", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method when cancel is pressed, goes back to previous screen
     */
    private void cancelSelected() {
        onBackPressed();
    }

    /**
     * Method when delete is pressed, will try to delete current stage.
     */
    private void deleteSelected() {
        if(editMode){
            stage.setName(stageId);
            viewModel.deleteStage(stage, new OnAsyncEventListener() {
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
