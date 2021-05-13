package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class EditingProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        View saving_date_btn= findViewById(R.id.btn_saving_date);
        View exit_btn = findViewById(R.id.btn_exit);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMain(); }
        });

        saving_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showProfile(); }
        });
    }

     private void showProfile() {
         Intent intent = new Intent(this, ProfileActivity.class);
         startActivity(intent);
     }

      private void showMain() {
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item); }
    }
}