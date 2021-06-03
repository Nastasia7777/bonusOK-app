package org.hse.bonusokapplication.Utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FirebaseMessagingApi {

    //отправка токена устройства
    @POST("/api/Client/{clientId}/AddDevice/{token}")
    Call<Void> addDevice(@Path("clientId") int clientId, @Path("token") String token);

 }
