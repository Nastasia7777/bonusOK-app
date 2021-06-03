package org.hse.bonusokapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationActivity extends BaseClientActivity {

    EditText phone_input_text;
    private String phone_number;
    private String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        View sending_sms_btn = findViewById(R.id.btn_sending_sms);
        phone_input_text = findViewById(R.id.phone_input);
        sending_sms_btn.setEnabled(false);

        //Пользователь вводит номер телефона
        phone_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Проверяем длину введенного номера и удаляем из него лишние символы
                phone_number = phone_input_text.getText().toString();
                if (checkPhoneNumber(phone_number)) sending_sms_btn.setEnabled(true);
                else sending_sms_btn.setEnabled(false);
                phone_number = deleteSymbolsFromPhoneNumber(phone_number);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    //Отправление смс-кода
    public void sendCodeOnClick(View view){
        makeUserRegistrationApiCall(phone_number);
    }

    //Отправление смс-кода пользователю
    public void makeUserRegistrationApiCall (String phone)
    {
        ClientApi clientApi = Service.getClientApi();
        retrofit2.Call<Void> responseCall = clientApi.postNewUser(phone);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
              switch (response.code()){
                  case 200: //успешная регистрация
                      prefs.savePhoneNumber(phone);
                      showConfirmation();
                      Log.d(TAG, "the response code is " + response.code());
                      break;
                  case 400: //неверный формат номера
                      Toast.makeText(getApplicationContext(), R.string.invalid_phone_number, Toast.LENGTH_SHORT).show();
                      break;
                  case 409: //клиент с таким номером уже существует
                      Toast.makeText(getApplicationContext(), R.string.user_already_exist, Toast.LENGTH_SHORT).show();
                      Log.d(TAG, "the response code is " + response.code());
                      break;
              }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
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