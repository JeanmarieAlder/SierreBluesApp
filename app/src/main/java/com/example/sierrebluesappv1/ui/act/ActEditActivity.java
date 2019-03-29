package com.example.sierrebluesappv1.ui.act;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;

public class ActEditActivity extends AppCompatActivity {

    private long actId;
    private ActEntity act;
    private ActViewModel viewModel;
    private boolean editMode;
    private ArrayAdapter<CharSequence> adapter;

    private EditText eaName;
    private EditText eaCountry;
    private EditText egenre;
    private Spinner edate;
    private EditText estartTime;
    private EditText eprice;
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
        estage = findViewById(R.id.act_edit_text_stage);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.dates, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edate.setAdapter(adapter);

        buttonSave = findViewById(R.id.edit_act_save_button);
        buttonCancel = findViewById(R.id.edit_act_cancel_button);
        buttonDelete = findViewById(R.id.edit_act_delete_button);

        buttonSave.setOnClickListener(view -> {
            saveChanges();
        });
        buttonCancel.setOnClickListener(view -> {
            cancelSelected();
        });
        buttonDelete.setOnClickListener(view -> {
            deleteSelected();

        });

        //get act ID from intent and set edit mode to false if new act
        actId = getIntent().getLongExtra("actId", 0l);
        editMode = getIntent().getBooleanExtra("isEdit", true);
    }

    private void saveChanges() {
        String timeCheck = estartTime.getText().toString();
        if(timeCheck.indexOf(':') == -1 ||
                timeCheck.indexOf(':') != timeCheck.lastIndexOf(':'))
        {
            Toast.makeText(getApplicationContext(), "Invalid start time", Toast.LENGTH_LONG).show();
            return;
        }
        if(editMode){
            act.setArtistName(eaName.getText().toString());
            act.setArtistCountry(eaCountry.getText().toString());
            act.setArtistImage("Not implemented yet");
            act.setGenre(egenre.getText().toString());
            act.setDate(edate.getSelectedItem().toString());
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
            act.setIdStage(estage.getText().toString());

            viewModel.updateAct(act, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(),
                            "Update succesful", Toast.LENGTH_LONG).show();
                    onBackPressed(); //finally, go back to previous screen
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
            newAct.setGenre(egenre.getText().toString());
            newAct.setDate(edate.getSelectedItem().toString());
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
            newAct.setIdStage(estage.getText().toString());
            viewModel.createAct(newAct, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "Creation succesful", Toast.LENGTH_LONG).show();
                    onBackPressed(); //finally, go back to previous screen
                }

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
            switch (act.getDate()){
                case "Friday":
                    edate.setSelection(0);
                    break;
                case "Saturday":
                    edate.setSelection(1);
                    break;
                case "Sunday":
                    edate.setSelection(2);
            }
            estartTime.setText(act.getStartTime());
            eprice.setText(String.valueOf(act.getPrice()));
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
        }
        onBackPressed(); //finally, go back to previous screen

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
