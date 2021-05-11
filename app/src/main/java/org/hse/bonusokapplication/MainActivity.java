package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View registration_btn= findViewById(R.id.btn_registration);
        View enter_btn = findViewById(R.id.btn_enter);

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistration();
            }
        });

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnter();
            }
        });
    }

    private void showRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void showEnter() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }
}