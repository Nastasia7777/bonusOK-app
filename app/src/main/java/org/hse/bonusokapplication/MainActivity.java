package org.hse.bonusokapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.PromoApi;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends RootActivity {

    private PromoListViewModel promoListViewModel;
    private PreferenceManager prefs;
    private int clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //проверка сохранренного токена. Если есть, то открываем профиль
        prefs = new PreferenceManager(this);
        //prefs.deleteAllPreferences();
        String s = prefs.getToken();
        if (prefs.getToken() != "") {
            Intent intent = new Intent(this, MenuActivity.class);
            clientId = prefs.getClientModel().getId();
            intent.putExtra(MenuActivity.CLIENT_ID, clientId);
            this.finish();
            startActivity(intent);
            return;
        }

        View registration_btn= findViewById(R.id.btn_registration);
        View enter_btn = findViewById(R.id.btn_enter);
        promoListViewModel = ViewModelProviders.of(this).get(PromoListViewModel.class);
       // ObserveAnyChange();

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegistration();
            }
        });

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnter();
            }
        });
    }

    private void showRegistration() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void showEnter() {
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }


    private void ObserveAnyChange()
    {
        promoListViewModel.getPromoListObserver().observe(this, new Observer<List<PromoModel>>() {
            @Override
            public void onChanged(List<PromoModel> promoModels) {
                // observing for any promo data change
                if(promoModels!=null)
                    for(PromoModel model: promoModels){
                        Log.d("TAG", "onChanged: "+model.getDescription());
                    }
            }
        });
        promoListViewModel.searchPromoApi(clientId);
    }

}