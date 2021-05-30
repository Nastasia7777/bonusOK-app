package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;
import org.hse.bonusokapplication.ViewModels.ClientViewModel;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseClientActivity extends AppCompatActivity {

    protected ClientViewModel clientViewModel;
    protected PreferenceManager prefs;
    //protected ClientRepository clientRepository;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
        prefs = new PreferenceManager(this);
        //clientRepository = ClientRepository.getInstance();

    }

    public boolean checkPhoneNumber(String phone){ return phone.contains("_") ? false : true; }

    public boolean checkCode(String code){
        return code.length() == 4 ? true : false;
    }

    public String deleteSymbolsFromPhoneNumber(String phone_number){
        return phone_number.replaceAll("\\D", "");
    }

    //Токен
    public void makeTokenApiCall (String phone, String sms_code)
    {
        String TAG = "makeTokenApiCall";
        ClientApi clientApi = Service.getClientApi();
        Call<DeviceModel> responseCall = clientApi.getToken(phone, sms_code);
        responseCall.enqueue(new Callback<DeviceModel>() {
            @Override
            public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {
                if(response.code() == 200){
                    clientViewModel.deviceModel.postValue((DeviceModel) response.body());
                    Log.d(TAG, "the response code is " + response.code());
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
        String TAG = "makeClientApiCall";
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
        String TAG = "makeClientCardApiCall";
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

    protected void showProfile() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
