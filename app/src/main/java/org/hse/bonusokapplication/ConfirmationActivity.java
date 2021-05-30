package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import org.hse.bonusokapplication.Models.DeviceModel;
import org.w3c.dom.Text;

public class ConfirmationActivity extends BaseClientActivity {

    private PreferenceManager prefs;
    EditText code_input;
    Button enter2_profile_btn;
    private String phone_number, sms_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        enter2_profile_btn = findViewById(R.id.btn_enter2_profile);
        code_input = findViewById(R.id.code_input);
        enter2_profile_btn.setEnabled(false);

        prefs = new PreferenceManager(this);
        phone_number = prefs.getPhoneNumber();

        code_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Проверям длину смс-кода
                sms_code = code_input.getText().toString();
                if (checkCode(sms_code)) enter2_profile_btn.setEnabled(true);
                else enter2_profile_btn.setEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public void enterToProfileOnClick(View view){
        observeAnyChangeAboutToken();
        makeTokenApiCall(phone_number, sms_code);
    }

    private void observeAnyChangeAboutToken(){
        clientViewModel.deviceModel.observe(this, new Observer<DeviceModel>() {
            @Override
            public void onChanged(DeviceModel device) {
                if (device.getToken() == null) return;
                prefs.saveToken(device.getToken());
                showProfile();
            }
        });
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