package org.hse.bonusokapplication.Repositories;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import org.hse.bonusokapplication.AuthorizationActivity;
import org.hse.bonusokapplication.MainActivity;
import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.PreferenceManager;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.ClientApi;
import org.hse.bonusokapplication.Utils.PromoApi;
import org.hse.bonusokapplication.ViewModels.ClientViewModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientRepository {

    public static final String TAG = "ClientRepository";

    private static ClientRepository instance;
    private static DeviceModel device;
    private static ClientModel client;
    private static CardModel card;

    public static ClientRepository getInstance(){
        if(instance == null){
            instance = new ClientRepository();
        }
        return instance;
    }

    //Токен
    /*public DeviceModel makeTokenApiCall (String phone, String sms_code)
    {
        device = new DeviceModel();
        ClientApi clientApi = Service.getClientApi();
        Call<DeviceModel> responseCall = clientApi.getToken(phone, sms_code);
        responseCall.enqueue(new Callback<DeviceModel>() {
            @Override
            public void onResponse(Call<DeviceModel> call, Response<DeviceModel> response) {
                if(response.code() == 200){
                    device = (DeviceModel) response.body();
                }
                else{
                    Log.d(TAG, "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DeviceModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
        return device;
    }*/

    /*public ClientModel makeClientApiCall (int user_id, String token)
    {
        client = new ClientModel();
        ClientApi clientApi = Service.getClientApi();
        Call<ClientModel> responseCall = clientApi.getClient(user_id, token);
        responseCall.enqueue(new Callback<ClientModel>() {
            @Override
            public void onResponse(Call<ClientModel> call, Response<ClientModel> response) {
                if(response.code() == 200){
                    client = (ClientModel) response.body();
                }
                else{
                    Log.d(TAG, "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ClientModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
        return client;
    }*/

    /*public CardModel makeClientCardApiCall (int user_id, String token)
    {
        card = new CardModel();
        ClientApi clientApi = Service.getClientApi();
        Call<CardModel> responseCall = clientApi.getClientCard(user_id, token);
        responseCall.enqueue(new Callback<CardModel>() {
            @Override
            public void onResponse(Call<CardModel> call, Response<CardModel> response) {
                if(response.code() == 200){
                    card = (CardModel) response.body();
                }
                else{
                    Log.d(TAG, "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CardModel> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });
        return card;
    }*/
}
