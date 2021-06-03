package org.hse.bonusokapplication.Request;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hse.bonusokapplication.PreferenceManager;
import org.hse.bonusokapplication.Utils.ClientApi;
import org.hse.bonusokapplication.Utils.Credentials;
import org.hse.bonusokapplication.Utils.FirebaseMessagingApi;
import org.hse.bonusokapplication.Utils.PromoApi;
import org.hse.bonusokapplication.ViewModels.ClientViewModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static boolean isUserLoggedIn(){

        // Проверяем, авторизован ли пользователь
        return true;
    }

    private static String getToken(){

        // Возвращаем строку в формате: Bearer <Jwt-токен>
        return "Bearer ";
    }

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("Accept", "application/json;versions=1");
                    if (isUserLoggedIn()) {
                        ongoing.addHeader("Authorization", getToken());
                    }
                    return chain.proceed(ongoing.build());
                }
            })
            .build();

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static Retrofit retrofit = retrofitBuilder.build();

    private static PromoApi promoApi = retrofit.create(PromoApi.class);
    private static ClientApi clientApi = retrofit.create(ClientApi.class);
    private static FirebaseMessagingApi firebaseMessagingApi = retrofit.create(FirebaseMessagingApi.class);

    public static PromoApi getPromoApi(){
        return promoApi;
    }
    public static  ClientApi getClientApi() { return clientApi; }
    public static FirebaseMessagingApi getFirebaseMessagingApi() {return firebaseMessagingApi;}
}
