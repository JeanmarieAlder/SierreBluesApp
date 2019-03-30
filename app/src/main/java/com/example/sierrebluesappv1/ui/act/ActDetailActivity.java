package com.example.sierrebluesappv1.ui.act;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sierrebluesappv1.ui.nav.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.ui.settings.SettingsActivity;
import com.example.sierrebluesappv1.database.entity.ActEntity;
import com.example.sierrebluesappv1.ui.stage.StageDetailActivity;
import com.example.sierrebluesappv1.util.OnAsyncEventListener;
import com.example.sierrebluesappv1.viewmodel.act.ActViewModel;

public class ActDetailActivity extends AppCompatActivity {

    private long actId;
    private boolean isUser;
    private ActEntity act;
    private ActViewModel viewModel;

    private Button buttonEdit;
    private Button buttonDelete;
    private Button buttonCancel;

    private TextView tvName;
    private TextView tvCountry;
    private TextView tvGenre;
    private TextView tvDateStartTime;
    private TextView tvPrice;
    private TextView tvStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_detail);

        //initializes views references, buttons and get id from the act
        initialize();
        //Create viewmodel for the specific act
        //Observer will check if data has changed and notify updateContent()
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

    /**
     * Updates the data that the user sees. Called on ViewModel creation
     * or whenever data changes.
     */
    private void updateContent() {
        if (act != null) {
            tvName.setText(act.getArtistName());
            tvCountry.setText(act.getArtistCountry());
            tvGenre.setText(act.getGenre());
            tvDateStartTime.setText(act.getDate() + " - " + act.getStartTime());
            tvPrice.setText(String.valueOf(act.getPrice()));
            //retrieved from:
            //https://stackoverflow.com/questions/2394935/can-i-underline-text-in-an-android-layout
            //Underlines the stage name to show that it is clickable.
            SpannableString content = new SpannableString(act.getIdStage());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tvStage.setText(content);

        }
    }

    /**
     * Initializes views, buttons and IDs
     */
    private void initialize() {

        tvName = findViewById(R.id.act_detail_text_name);
        tvCountry = findViewById(R.id.act_detail_text_country);
        tvGenre = findViewById(R.id.act_detail_text_genre);
        tvDateStartTime = findViewById(R.id.act_detail_text_date);
        tvPrice = findViewById(R.id.act_detail_text_price);
        tvStage = findViewById(R.id.act_detail_text_stage);

        buttonEdit = findViewById(R.id.detail_act_edit_button);
        buttonDelete = findViewById(R.id.detail_act_delete_button);
        buttonCancel = findViewById(R.id.detail_act_cancel_button);

        //If user (not admin), get rid of edit and delete button.
        isUser = getIntent().getBooleanExtra("isUser", false);
        if(isUser){
            buttonDelete.setVisibility(View.GONE);
            buttonEdit.setVisibility(View.GONE);
        }else{
            buttonEdit.setOnClickListener(view -> edit(act.getIdAct()));

            buttonDelete.setOnClickListener(view -> deleteSelected());
        }
        buttonCancel.setOnClickListener(view -> onBackPressed()); //cancel button goes to previous activity
        tvStage.setOnClickListener(view -> {
            //if stagename is clicked, open the stage detail activity with selected stage
            Intent intent = new Intent(ActDetailActivity.this, StageDetailActivity.class);
            intent.putExtra("stageId", tvStage.getText().toString());
            intent.putExtra("isUser", isUser);
            startActivity(intent);
        });

        //get act ID from intent and set edit mode to false if new act
        actId = getIntent().getLongExtra("actId", 0l);
    }

    /**
     * Called when delete button is pressed,
     * tries to delete the current act from DB
     */
    private void deleteSelected() {
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
        onBackPressed(); //Back tot previous screen after deletion
    }

    /**
     * Edit the current act. Opens the actEditActivity with the current act.
     * @param idAct current act ID
     */
    private void edit(Long idAct) {
        Intent intent = new Intent(ActDetailActivity.this, ActEditActivity.class);
        intent.putExtra("actId", idAct);
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
                intent = new Intent(ActDetailActivity.this, AboutActivity.class);
                ActDetailActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(ActDetailActivity.this, SettingsActivity.class);
                ActDetailActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
