package org.hse.bonusokapplication.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.hse.bonusokapplication.Models.PromoModel;
//import org.hse.bonusokapplication.Request.PromoApiClient;
import org.hse.bonusokapplication.Request.Service;
import org.hse.bonusokapplication.Utils.PromoApi;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoRepository {

    public static final String TAG = "PromoRepositoryTAG";

    private static PromoRepository instance;

    public static PromoRepository getInstance(){
        if(instance == null){
            instance = new PromoRepository();
        }
        return instance;
    }

    private static List<PromoModel> promoList = new ArrayList<>();

    public List<PromoModel> makePromoApiCall(){

        PromoApi promoApi = Service.getPromoApi();
        Call<List<PromoModel>> responseCall = promoApi.searchPromos(5);
        responseCall.enqueue(new Callback<List<PromoModel>>() {
            @Override
            public void onResponse(Call<List<PromoModel>> call, Response<List<PromoModel>> response) {
                if(response.code() ==200){
                    Log.d(TAG, "the response: "+response.body().toString());
                    promoList = (List<PromoModel>) response.body();
                }
                else{
                    Log.d(TAG, "the error: "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<PromoModel>> call, Throwable t) {
                Log.d(TAG, "on failure: "+t.getMessage());
            }
        });

        return promoList;
    }
}
