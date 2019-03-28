package com.example.sierrebluesappv1.ui.act;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.WelcomeActivity;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;

public class ActEditActivity extends AppCompatActivity {

    private long actId;
    private ActEntity act;
    private ActViewModel viewModel;
    private boolean editMode;

    private EditText eaName;
    private EditText eaCountry;
    private EditText egenre;
    private EditText edate;
    private EditText estartTime;
    private EditText eprice;
    private EditText eaWebsite;
    private EditText estage;

    private Button buttonSave;
    private Button buttonDelete;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_act);

        initialize();



        ActViewModel.Factory factory = new ActViewModel.Factory(
                getApplication(), actId);
        viewModel = ViewModelProviders.of(this, factory).get(ActViewModel.class);
        viewModel.getAct().observe(this, actEntity -> {
            if (actEntity != null) {
                act = actEntity;
                updateContent();
            }
        });
    }



    private void initialize() {
        eaName = findViewById(R.id.act_edit_text_name);
        eaCountry = findViewById(R.id.act_edit_text_country);
        egenre = findViewById(R.id.act_edit_text_genre);
        edate = findViewById(R.id.act_edit_text_date);
        estartTime = findViewById(R.id.act_edit_text_stime);
        eprice = findViewById(R.id.act_edit_text_price);
        eaWebsite = findViewById(R.id.act_edit_text_website);
        estage = findViewById(R.id.act_edit_text_stage);

        buttonSave = findViewById(R.id.edit_act_save_button);
        buttonCancel = findViewById(R.id.edit_act_cancel_button);
        buttonDelete = findViewById(R.id.edit_act_delete_button);

        buttonSave.setOnClickListener(view -> {
            saveChanges();
            onBackPressed();
        });
        buttonCancel.setOnClickListener(view -> {
            cancelSelected();
        });
        buttonDelete.setOnClickListener(view -> {
            deleteSelected();
            onBackPressed();
        });

        //get act ID from intent and set edit mode to false if new act
        actId = getIntent().getLongExtra("actId", 0l);
        editMode = getIntent().getBooleanExtra("isEdit", true);
    }

    private void saveChanges() {
        if(editMode){
            act.setArtistName(eaName.getText().toString());
            act.setArtistCountry(eaCountry.getText().toString());
            act.setDescription("Not implemented yet");
            act.setGenre(egenre.getText().toString());
            act.setDate(edate.getText().toString());
            act.setStartTime(estartTime.getText().toString());

            //convert string from float.
            //https://stackoverflow.com/questions/10666783/how-to-cast-from-editable-to-float
            String pr = eprice.getText().toString();
            Float f;
            try{
                f = new Float(pr);
                act.setPrice(f);
            }catch(Exception e){
                e.getMessage();
                act.setPrice(0f);
            }

            act.setArtistWebsite(eaWebsite.getText().toString());
            act.setIdStage(estage.getText().toString());

            viewModel.updateAct(act, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Update succesful", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception e) {
                    if(e.getMessage().contains("FOREIGN KEY")){
                        Toast.makeText(getApplicationContext(),
                                "Update error: stage name doesn't exist",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Update failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            ActEntity newAct = new ActEntity();
            newAct.setArtistName(eaName.getText().toString());
            newAct.setArtistCountry(eaCountry.getText().toString());
            newAct.setDescription("Not implemented yet");
            newAct.setGenre(egenre.getText().toString());
            newAct.setDate(edate.getText().toString());
            newAct.setStartTime(estartTime.getText().toString());

            //convert string from float.
            //https://stackoverflow.com/questions/10666783/how-to-cast-from-editable-to-float
            String pr = eprice.getText().toString();
            Float f;
            try{
                f = new Float(pr);
                newAct.setPrice(f);
            }catch(Exception e){
                e.getMessage();
                newAct.setPrice(0f);
            }

            newAct.setArtistWebsite(eaWebsite.getText().toString());
            newAct.setIdStage(estage.getText().toString());
            viewModel.createAct(newAct, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {Toast.makeText(getApplicationContext(), "Creation succesful", Toast.LENGTH_LONG).show();}

                @Override
                public void onFailure(Exception e) {
                    if(e.getMessage().contains("FOREIGN KEY")){
                        Toast.makeText(getApplicationContext(), "Creation error: stage name doesn't exist", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Creation failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void updateContent() {
        if (act != null) {
            eaName.setText(act.getArtistName());
            eaCountry.setText(act.getArtistCountry());
            egenre.setText(act.getGenre());
            edate.setText(act.getDate());
            estartTime.setText(act.getStartTime());
            eprice.setText(String.valueOf(act.getPrice()));
            eaWebsite.setText(act.getArtistWebsite());
            estage.setText(act.getIdStage());

        }
    }


    private void deleteSelected(){
        if(editMode){
            viewModel.deleteAct(act, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Act deleted", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplicationContext(), "Error, couldn't delete the act", Toast.LENGTH_LONG).show();
                }
            });
        }else{
            onBackPressed();
        }

    }

    private void cancelSelected(){
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
                intent = new Intent(ActEditActivity.this, AboutActivity.class);
                ActEditActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(ActEditActivity.this, SettingsActivity.class);
                ActEditActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
