package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.Repositories.ClientRepository;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;
import org.hse.bonusokapplication.ViewModels.ClientViewModel;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity {

    Button get_code_btn, enter2_profile_btn;
    EditText phone_input_text, code_input_text;
    private String phone_number, sms_code;
    //private Boolean user_can_enter = false;

    private PreferenceManager prefs;
    //private ClientRepository clientRepository;
    private ClientViewModel clientViewModel;
    private String TAG = "AuthorizationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        prefs = new PreferenceManager(this);
        //clientRepository = ClientRepository.getInstance();
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        get_code_btn = findViewById(R.id.btn_get_code);
        enter2_profile_btn= findViewById(R.id.btn_enter2_profile);
        phone_input_text = findViewById(R.id.phone_input);
        code_input_text = findViewById(R.id.code_input);
        get_code_btn.setEnabled(false);
        enter2_profile_btn.setEnabled(false);
        code_input_text.setEnabled(false);

        //Пользователь вводит номер телефона
        phone_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Проверяем длину введенного номера и удаляем из него лишние символы
                phone_number = phone_input_text.getText().toString();
                if (checkPhoneNumber()) get_code_btn.setEnabled(true);
                else get_code_btn.setEnabled(false);
                phone_number = deleteSymbolsFromPhoneNumber(phone_number);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Пользователь вводит смс-код
        code_input_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Проверям длину смс-кода
                sms_code = code_input_text.getText().toString();
                if (checkCode()) enter2_profile_btn.setEnabled(true);
                else enter2_profile_btn.setEnabled(false);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    public boolean checkPhoneNumber(){ return phone_number.contains("_") ? false : true; }

    public boolean checkCode(){
        return sms_code.length() == 4 ? true : false;
    }

    public String deleteSymbolsFromPhoneNumber(String phone_number){
        return phone_number.replaceAll("\\D", "");
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

    // Получить смс-код
    public void getCodeOnClick(View view){
        if (phone_number == null) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        prefs.savePhoneNumber(phone_number);
        observeAnyChangeAboutUserStatus();
    }

    private void observeAnyChangeAboutUserStatus(){
        clientViewModel.user_is_registered.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isUserReg) {
                if (isUserReg) {
                    Log.d(TAG, "TRUE" );
                    code_input_text.setEnabled(true);
                }
                else {
                Log.d(TAG, "FALSE" );
                    code_input_text.setEnabled(false);
                    Toast.makeText(getApplicationContext(), R.string.user_is_unregistered, Toast.LENGTH_SHORT).show();
                }
            }
        });
        makeAuthCodeApiCall(phone_number);
    }

    //Отправление смс-кода пользователю
    public void makeAuthCodeApiCall (String phone)
    {
        ClientApi clientApi = Service.getClientApi();
        retrofit2.Call<Void> responseCall = clientApi.getAuthCode(phone);
        responseCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d(TAG, "the response code is " + response.code());
                    clientViewModel.user_is_registered.postValue(true);
                }
                else { //code = 404
                    Log.d(TAG, "the response code is " + response.code());
                    clientViewModel.user_is_registered.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    //Получаем данные пользователя и входим в профиль
    public void enterToProfileOnClick (View view) {
        if (phone_number == null) {
            Toast.makeText(this, R.string.input_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        if (sms_code == null) {
            Toast.makeText(this, R.string.input_sms_code, Toast.LENGTH_SHORT).show();
            return;
        }
        observeAnyChangeAboutToken();
    }

    private void observeAnyChangeAboutToken(){
        clientViewModel.deviceModel.observe(this, new Observer<DeviceModel>() {
            @Override
            public void onChanged(DeviceModel device) {
                if (device.getToken() == null) return;
                prefs.saveToken(device.getToken());
                getClientData(device.getUserId(), device.getToken());
                getClientCard(device.getUserId(), device.getToken());
            }
        });
        makeTokenApiCall(phone_number, sms_code);
    }

    public void getClientData(int clientId, String token){
        clientViewModel.clientModel.observe(this, new Observer<ClientModel>() {
            @Override
            public void onChanged(ClientModel client) {
                if (client != null)
                    prefs.saveClientModel(client);
            }
        });
        makeClientApiCall(clientId, token);
    }

    public void getClientCard(int clientId, String token){
        clientViewModel.cardModel.observe(this, new Observer<CardModel>() {
            @Override
            public void onChanged(CardModel card) {
                //сохранить в преференсы
                if (card == null) {
                    Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                prefs.saveCardModel(card);
                showProfile();
            }
        });
        makeClientCardApiCall(clientId, token);
    }

    //Токен
    public void makeTokenApiCall (String phone, String sms_code)
    {
        ClientApi clientApi = Service.getClientApi();
        Call<DeviceModel> responseCall = clientApi.getToken(phone, sms_code);
        responseCall.enqueue(new Callback<DeviceModel>() {
            @Override
            public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {
                if(response.code() == 200){
                    clientViewModel.deviceModel.postValue((DeviceModel) response.body());
                    Log.d(TAG, "get token, the response code is " + response.code());
                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.invalid_code, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DeviceModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    public void makeClientApiCall (int user_id, String token)
    {
        ClientApi clientApi = Service.getClientApi();
        Call<ClientModel> responseCall = clientApi.getClient(user_id, token);
        responseCall.enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                if(response.code() == 200){
                    clientViewModel.clientModel.postValue((ClientModel) response.body());
                    Log.d(TAG, "get client, the response code is " + response.code());
                }
                else{ //403, неверный токен
                    Log.d(TAG, "invalid token, the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    public void makeClientCardApiCall (int user_id, String token)
    {
        ClientApi clientApi = Service.getClientApi();
        Call<CardModel> responseCall = clientApi.getClientCard(user_id, token);
        responseCall.enqueue(new Callback<CardModel>() {
            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                switch (response.code()){
                    case 200:
                        clientViewModel.cardModel.postValue((CardModel) response.body());
                        Log.d(TAG, "get card, the response code is " + response.code());
                        break;
                    case 400:
                        Log.d(TAG, "client doesn`t have a card, the error: "+response.errorBody().toString());
                        break;
                    case 403:
                        Log.d(TAG, "invalid token, the error: "+response.errorBody().toString());
                        break;
                    case 404:
                        Log.d(TAG, "client doesn`t exist, the error: "+response.errorBody().toString());
                        break;
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
    }

    private void showProfile() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
