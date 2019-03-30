package com.example.sierrebluesappv1.ui.nav;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.ui.settings.SettingsActivity;
import com.example.sierrebluesappv1.ui.act.ActsActivity;
import com.example.sierrebluesappv1.ui.stage.StagesActivity;

/**
 * Admin part activity
 */
public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
                intent = new Intent(DashboardActivity.this, AboutActivity.class);
                DashboardActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(DashboardActivity.this, SettingsActivity.class);
                DashboardActivity.this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void actsSelected(View view)
    {
        Intent intent = new Intent(this, ActsActivity.class);
        startActivity(intent);
    }

    public void scenesSelected(View view)
    {
        Intent intent = new Intent(this, StagesActivity.class);
        startActivity(intent);
    }
}
