package org.hse.bonusokapplication.Push;

import android.util.Log;
import android.widget.Toast;

import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.R;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.FirebaseMessagingApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceIdSender {
    public void saveDevice(int clientId, String deviceToken) {
        String TAG = "saveDevice";
        FirebaseMessagingApi api = Service.getFirebaseMessagingApi();
        Call responseCall = api.addDevice(clientId, deviceToken);
        responseCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    Log.d(TAG, "the response code is " + response.code());
                } else {
                    Log.d(TAG, "the error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "on failure: " + t.getMessage());
            }
        });
    }

    public void deleteDevice(int clientId, String deviceToken) {
        String TAG = "deleteDevice";
        FirebaseMessagingApi api = Service.getFirebaseMessagingApi();
        Call responseCall = api.deleteDevice(clientId, deviceToken);
        responseCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200) {
                    Log.d(TAG, "the response code is " + response.code());
                } else {
                    Log.d(TAG, "the error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "on failure: " + t.getMessage());
            }
        });
    }
}
