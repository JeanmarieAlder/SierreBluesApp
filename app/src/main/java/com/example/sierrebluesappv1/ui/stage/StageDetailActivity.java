package com.example.sierrebluesappv1.ui.stage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sierrebluesappv1.AboutActivity;
import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.SettingsActivity;

public class StageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage_detail);
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
