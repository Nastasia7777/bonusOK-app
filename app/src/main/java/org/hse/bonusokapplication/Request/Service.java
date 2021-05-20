package org.hse.bonusokapplication.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hse.bonusokapplication.Utils.Credentials;
import org.hse.bonusokapplication.Utils.PromoApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PromoApi cardApi = retrofit.create(PromoApi.class);

    public static PromoApi getCardApi(){
        return cardApi;
    }
}
