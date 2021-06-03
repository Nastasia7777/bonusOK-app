package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText sms_text = findViewById(R.id.sms);
        int maxLength = 4;
        sms_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

         View  enter1_profile_btn = findViewById(R.id.btn_enter1_profile);
            enter1_profile_btn.setEnabled(false);


              sms_text.addTextChangedListener(new TextWatcher() {

                   public void afterTextChanged(Editable s) {
                   }

                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   }

                   public void onTextChanged(CharSequence s, int start,
                                             int before, int count) {
                       if (sms_text.getText().length() == 4 ) {
                           enter1_profile_btn.setEnabled(true);
                           enter1_profile_btn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                         showProfile();
                        }});
                       }
                   }
               });

        TextView mTimer;

        Boolean hasTimerFinished = false;

        mTimer = (TextView) findViewById(R.id.send_code);

        mTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasTimerFinished) {
                    // Повторный запрос
                }
            }
        });
        ConfirmationActivity context = this;

        //Создаем таймер обратного отсчета на 20 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        new CountDownTimer(20000, 1000) {

            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                mTimer.setText("Осталось: "
                        + millisUntilFinished / 1000);
            }

            //Задаем действия после завершения отсчета (высвечиваем надпись "Бабах!"):
            public void onFinish() {
                mTimer.setText("Отправить повторно");
                mTimer.setTextColor(ContextCompat.getColor(context, R.color.link));
                mTimer.setClickable(true);
                mTimer.setFocusable(true);
            }
        }
                .start();
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