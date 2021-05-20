package org.hse.bonusokapplication.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.Request.PromoApiClient;
import org.hse.bonusokapplication.ViewModels.PromoListViewModel;

import java.util.List;

public class PromoRepository {

    private PromoApiClient promoApiClient;

    private static PromoRepository instance;

    public static PromoRepository getInstance(){
        if(instance == null){
            instance = new PromoRepository();
        }
        return instance;
    }

    private PromoRepository (){
        promoApiClient = PromoApiClient.getInstance();
    }

    public LiveData<List<PromoModel>> getPromos(){
        return promoApiClient.getPromos();
    }

    public void searchPromoApi(){
        promoApiClient.searchPromosApi();
    }


}
