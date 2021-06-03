package org.hse.bonusokapplication.Utils;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.PromoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface PromoApi {

    // Making search for particular client's promos
    // Client 5 has 3 promos
    @GET("api/Client/{id}/Promo")
    Call<List<PromoModel>> searchPromos(@Path("id") int id);

    // Making search for particular client's promo by id
    @GET("api/Client/{id}/Promo/{promoId}")
    Call<PromoModel> searchPromo(@Path("id") int clientId, @Path("promoId") int promoId);
}
