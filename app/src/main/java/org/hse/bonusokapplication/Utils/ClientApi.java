package org.hse.bonusokapplication.Utils;

import com.google.gson.Gson;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientApi {

    //получить клиента по id
    @GET("api/Client/{id}")
    Call<ClientModel> getClient (@Path("id") int id,  @Header("Authorization") String token);

    //получить карту клиента по id
    @GET("api/Client/{clientId}/Card")
    Call<CardModel> getClientCard (@Path("clientId") int id, @Header("Authorization") String token);

    //отправка смс-сообщения с кодом
    @GET("api/Auth/request_code")
    Call<Void> getAuthCode (@Query("number") String phoneNumber);

    //получить токен и id клиента
    @POST("api/Auth/token")
    Call<DeviceModel> getToken (@Query("number") String phoneNumber, @Query("code") String code);

    //регистрация пользователя
    @POST("/api/Auth/register")
    Call<Void> postNewUser (@Query("number") String phoneNumber);
}
