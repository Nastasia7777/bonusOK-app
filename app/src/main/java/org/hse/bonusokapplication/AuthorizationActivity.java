package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.sapereaude.maskedEditText.MaskedEditText;


public class AuthorizationActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_authorization);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            EditText code_text = findViewById(R.id.code);
            int maxLength = 4;
            code_text.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});


            View enter2_profile_btn = findViewById(R.id.btn_enter2_profile);
            enter2_profile_btn.setEnabled(false);


               code_text.addTextChangedListener(new TextWatcher() {

                   public void afterTextChanged(Editable s) {
                   }

                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   }

                   public void onTextChanged(CharSequence s, int start,
                                             int before, int count) {
                       if (code_text.getText().length() == 4 ) {
                           enter2_profile_btn.setEnabled(true);
                           enter2_profile_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                         showProfile();
                         }});
                       }
                   }
               });

               View get_code_btn = findViewById(R.id.get_code);
               get_code_btn.setEnabled(false);
               MaskedEditText tel_text = findViewById(R.id.phone_input);

               tel_text.addTextChangedListener(new TextWatcher() {
                   public void afterTextChanged(Editable s) {}

                   public void beforeTextChanged(CharSequence s, int start,int count, int after) {}

                   public void onTextChanged(CharSequence s, int start,
                                             int before, int count) {
                       if (tel_text.getRawText().length() == 10 ) {
                           get_code_btn.setEnabled(true);
                       }
                   }
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
                    return super.onOptionsItemSelected(item);
            }
        }
    }
