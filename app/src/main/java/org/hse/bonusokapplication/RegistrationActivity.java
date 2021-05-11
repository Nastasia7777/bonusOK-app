package org.hse.bonusokapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class RegistrationActivity extends AppCompatActivity {

          @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        View sending_sms_btn= findViewById(R.id.btn_sending_sms);

        sending_sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showConfirmation(); }
        });
    }

     private void showConfirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        startActivity(intent);
    }
}