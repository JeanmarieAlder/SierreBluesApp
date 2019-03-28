package com.example.sierrebluesappv1.ui.act;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.WelcomeActivity;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;

public class ActEditActivity extends AppCompatActivity {

    private long actId;
    private ActEntity act;
    private ActViewModel viewModel;

    private EditText eaName;
    private EditText eaCountry;
    private EditText egenre;
    private EditText edate;
    private EditText estartTime;
    private EditText eprice;
    private EditText eaWebsite;
    private EditText estage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_act);

        initialize();
        actId = getIntent().getLongExtra("actId", 0l);

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


    public void deleteSelected(View view){


    }

    public void saveSelected(View view){

    }
    public void cancelSelected(View view){
        Intent intent = new Intent(this, ActsActivity.class);
        startActivity(intent);
    }
}
