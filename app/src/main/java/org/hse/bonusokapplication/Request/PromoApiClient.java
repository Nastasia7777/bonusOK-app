package org.hse.bonusokapplication.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.hse.bonusokapplication.Models.PromoModel;
import org.hse.bonusokapplication.PromoApp.AppExecutors;
import org.hse.bonusokapplication.Utils.PromoApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PromoApiClient {

    private MutableLiveData<List<PromoModel>> mPromos ;

    private static PromoApiClient instance;

    private RetrivePromosRunnable retrivePromosRunnable;

    public static PromoApiClient getInstance(){
        if(instance == null){
            instance = new PromoApiClient();
        }
        return instance;
    }

    private PromoApiClient(){
        mPromos = new MutableLiveData<>();
    }

    public LiveData<List<PromoModel>> getPromos(){
        return mPromos;
    }

    public void searchPromosApi(){

        if(retrivePromosRunnable!=null){
            retrivePromosRunnable = null;
        }

        retrivePromosRunnable = new RetrivePromosRunnable();
        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrivePromosRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    // Retriving data from RESTAPI by runnable class
    private class RetrivePromosRunnable implements Runnable{

        boolean cancelRequest;

        public RetrivePromosRunnable(){
            // auth token
        }

        @Override
        public void run(){

            // Getting the response
            try {
                Response response = getPromos(5).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code()==200){
                    List<PromoModel> list = new ArrayList<>((List<PromoModel>)response.body());
                    Log.d("TAG", "the response: "+response.body().toString());
                    mPromos.postValue(list);
                } else{
                    mPromos.postValue(null);
                    Log.d("TAG", "the error: "+response.errorBody().toString());
                }

            } catch(Exception e){
                e.printStackTrace();
                mPromos.postValue(null);
            }
        }

        private Call<List<PromoModel>> getPromos(int clientId){
            return Service.getCardApi().searchPromos(clientId);
        }

        private void cancelRequest(){
            Log.d("TAG", "Cancelling search request");
            cancelRequest = true;
        }
    }
}
