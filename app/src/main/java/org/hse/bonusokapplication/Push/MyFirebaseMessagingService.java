package org.hse.bonusokapplication.Push;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.hse.bonusokapplication.DiscountFragment;
import org.hse.bonusokapplication.MenuActivity;
import org.hse.bonusokapplication.NotificationsManager;
import org.hse.bonusokapplication.PreferenceManager;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private PreferenceManager pref;

    private void saveToken(String token){

        pref = new PreferenceManager(getApplicationContext());
        pref.saveDeviceToken(token);
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("Refreshed token:",token);
        pref = new PreferenceManager(getApplicationContext());
        pref.saveDeviceToken(token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.d(TAG, "Notification JSON " + json.toString());
        try {
            //parsing json data
            String title = json.getString("title");
            String message = json.getString("message");

            //creating MyNotificationManager object
            NotificationsManager mNotificationManager = new NotificationsManager(getApplicationContext());

            pref = new PreferenceManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            int clientId = pref.getClientModel().getId();
            intent.putExtra(MenuActivity.CLIENT_ID, clientId);

            mNotificationManager.showSmallNotification(title, message, intent);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    public void sendDeviceToken(int clientId){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Device token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("Device token", token);
                        DeviceIdSender deviceIdSender = new DeviceIdSender();
                        deviceIdSender.saveDevice(clientId, token);
                    }
                });
    }

    public void deleteDeviceToken(int clientId){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Device token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("Device token", token);
                        DeviceIdSender deviceIdSender = new DeviceIdSender();
                        deviceIdSender.deleteDevice(clientId, token);
                    }
                });
    }
}
