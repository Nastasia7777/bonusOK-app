package org.hse.bonusokapplication.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.Repositories.PromoRepository;

import java.util.List;

public class PromoListViewModel extends ViewModel {

    private PromoRepository promoRepository;


    public PromoListViewModel(){
        promoRepository = PromoRepository.getInstance();
    }

    public LiveData<List<PromoModel>> getPromos(){
        return promoRepository.getPromos();
    }

    public void searchPromoApi(){
        promoRepository.searchPromoApi();
    }

}
