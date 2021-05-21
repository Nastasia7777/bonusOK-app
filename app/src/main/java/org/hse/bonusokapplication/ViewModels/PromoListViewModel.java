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

    private MutableLiveData<List<PromoModel>> promoList;

    public MutableLiveData<List<PromoModel>> getPromoListObserver(){
        return promoList;
    }

    public PromoListViewModel(){

        promoList = new MutableLiveData<>();
        promoRepository = PromoRepository.getInstance();
    }


    public void searchPromoApi(){
        promoList.postValue(promoRepository.makePromoApiCall());
    }

}
