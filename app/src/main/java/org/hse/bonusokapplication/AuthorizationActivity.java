package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

     View enter2_profile_btn= findViewById(R.id.btn_enter2_profile);

        enter2_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showProfile(); }
        });
    }

     private void showProfile() {
        Intent intent = new Intent(this, MenuActivity.class);
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