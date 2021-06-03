package org.hse.bonusokapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import br.com.sapereaude.maskedEditText.MaskedEditText;


public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        View sending_sms_btn = findViewById(R.id.btn_sending_sms);

        sending_sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmation();
            }
        });

        View send_sms_btn = findViewById(R.id.btn_sending_sms);
               send_sms_btn.setEnabled(false);
               MaskedEditText tel_text = findViewById(R.id.phone_input);

               tel_text.addTextChangedListener(new TextWatcher() {
                   public void afterTextChanged(Editable s) {}

                   public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

                   public void onTextChanged(CharSequence s, int start,
                                             int before, int count) {
                       if (tel_text.getRawText().length() == 10 ) {
                           send_sms_btn.setEnabled(true);
                       }
                   }
               });
    }

    private void showConfirmation() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
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