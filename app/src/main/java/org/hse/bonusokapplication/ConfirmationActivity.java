package org.hse.bonusokapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import org.hse.bonusokapplication.Models.DeviceModel;
import org.w3c.dom.Text;

public class ConfirmationActivity extends BaseClientActivity {

    private PreferenceManager prefs;
    EditText code_input;
    Button enter2_profile_btn;
    TextView mTimer;
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

        boolean hasTimerFinished = false;
        mTimer = findViewById(R.id.resend_code);

        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasTimerFinished) {
                    // Повторный запрос
                    startResendTimer();
                }
            }
        });

         startResendTimer();
    }

    public void startResendTimer ()
    {
        ConfirmationActivity context = this;

        mTimer.setClickable(false);
        mTimer.setFocusable(false);
     //Создаем таймер обратного отсчета на 20 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        new CountDownTimer(20000, 1000) {

            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                mTimer.setText("Осталось: "
                        + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTimer.setText("Отправить повторно");
                mTimer.setTextColor(ContextCompat.getColor(context, R.color.link));
                mTimer.setClickable(true);
                mTimer.setFocusable(true);
            }
        }
                .start();
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
                sendDeviceToken(device.getUserId());
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

    protected void showProfile(){
        super.showProfile();
        finishActivity("RegistrationActivity");
        this.finish();
    }
}