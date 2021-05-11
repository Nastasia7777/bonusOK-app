package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class EditingProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_profile);

        View saving_date_btn= findViewById(R.id.btn_saving_date);

        saving_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showProfile(); }
        });
    }

     private void showProfile() {
         Intent intent = new Intent(this, ProfileActivity.class);
         startActivity(intent);
     }
}