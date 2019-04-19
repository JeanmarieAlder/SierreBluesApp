package com.example.sierrebluesappv1.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sierrebluesappv1.R;
import com.example.sierrebluesappv1.ui.settings.SettingsActivity;
import com.example.sierrebluesappv1.ui.nav.WelcomeActivity;
import com.example.sierrebluesappv1.ui.nav.AboutActivity;
import com.example.sierrebluesappv1.ui.nav.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {

    private final String aName = "Login Page";
    private TextView title_view;
    private Button btn_user;
    private Button btn_admin;
    private Button btn_demo_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_view = findViewById(R.id.main_title_view);
        title_view.setText(aName);
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
                intent = new Intent(MainActivity.this, AboutActivity.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
                break;
        }
        return true;
    }

    public void userSelected(View view)
    {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void adminSelected(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    /**
     * Used when demo data button is pressed, reinitializes all data
     * using default data located in DatabaseInitializer class.
     * @param view
     */
    public void reinitializeDatabase(View view) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.alert_demo_data));
        alertDialog.setCancelable(false);
        alertDialog.setMessage(getString(R.string.alert_demo_data_text));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_reset), (dialog, which) ->{
            Toast.makeText(this, getString(R.string.demo_data_initiated), Toast.LENGTH_LONG).show();
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
        alertDialog.show();
    }

}
