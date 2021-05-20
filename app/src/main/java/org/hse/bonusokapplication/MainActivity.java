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

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://api.ipgeolocation.io/ipgeo?apiKey=b03018f75ed94023a005637878ec0977";
    private OkHttpClient client = new OkHttpClient();
    private PromoListViewModel promoListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View registration_btn= findViewById(R.id.btn_registration);
        View enter_btn = findViewById(R.id.btn_enter);
        promoListViewModel = ViewModelProviders.of(this).get(PromoListViewModel.class);
        ObserveAnyChange();

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
       // Intent intent = new Intent(this, AuthorizationActivity.class);
       // startActivity(intent);
        Log.d("TAG", "start");
   //     getRetrofitResponse();
    //    getRetrofitResponseById();
        searchPromoApi();
        Log.d("TAG", "end");
    }


    private void getRetrofitResponse(){

        PromoApi promoApi = Service.getCardApi();
        Call<List<PromoModel>> responseCall = promoApi.searchPromos(5);

        responseCall.enqueue(new Callback<List<PromoModel>>() {
            @Override
            public void onResponse(Call<List<PromoModel>> call, Response<List<PromoModel>> response) {
                Log.d("TAG", "in !");
                if(response.code() ==200){
                    Log.d("TAG", "the response: "+response.body().toString());
                }
                else{
                    Log.d("TAG", "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<PromoModel>> call, Throwable t) {
                Log.d("TAG", "on failure: "+t.getMessage());
            }
        });
    }

    private void getRetrofitResponseById(){
        PromoApi promoApi = Service.getCardApi();
        Call<PromoModel> responseCall = promoApi.searchPromo(5, 15);

        responseCall.enqueue(new Callback<PromoModel>() {
            @Override
            public void onResponse(Call<PromoModel> call, Response<PromoModel> response) {
                Log.d("TAG", "in !");
                if(response.code() ==200){
                    Log.d("TAG", "the response: "+response.body().getDescription());
                }
                else{
                    Log.d("TAG", "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PromoModel> call, Throwable t) {
                Log.d("TAG", "on failure: "+t.getMessage());
            }
        });
    }

    private void ObserveAnyChange()
    {
        promoListViewModel.getPromos().observe(this, new Observer<List<PromoModel>>() {
            @Override
            public void onChanged(List<PromoModel> promoModels) {
                // observing for any promo data change
                if(promoModels!=null)
                    for(PromoModel model: promoModels){
                        Log.d("TAG", "onChanged: "+model.getDescription());
                    }
            }
        });
    }

    private void searchPromoApi(){
        promoListViewModel.searchPromoApi();
    }
}