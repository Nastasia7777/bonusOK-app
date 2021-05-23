package org.hse.bonusokapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class ClientModel {

    private int id;
    private String phone;
    private String surname;
    private String name;
    private String email;
    private Date birthdate;

    private int cardId;
    private int authCode;
    private Date authCodeDeathTime;

    private CardModel card;
    private List<PromoModel> promos;

    public ClientModel(int id, String phone, String surname, String name, String email, Date birthdate, int cardId, int authCode, Date authCodeDeathTime, CardModel card, List<PromoModel> promos) {
        this.id = id;
        this.phone = phone;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.cardId = cardId;
        this.authCode = authCode;
        this.authCodeDeathTime = authCodeDeathTime;
        this.card = card;
        this.promos = promos;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public int getCardId() {
        return cardId;
    }

    public int getAuthCode() {
        return authCode;
    }

    public Date getAuthCodeDeathTime() {
        return authCodeDeathTime;
    }

    public CardModel getCard() {
        return card;
    }

    public List<PromoModel> getPromos() {return promos;}

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    protected ClientModel(Parcel in) {
//        id = in.readInt();
//        phone = in.readString();
//        surname = in.readString();
//        name = in.readString();
//        email = in.readString();
//        cardId = in.readInt();
//        authCode = in.readInt();
//    }
//
//    public static final Creator<ClientModel> CREATOR = new Creator<ClientModel>() {
//        @Override
//        public ClientModel createFromParcel(Parcel in) {
//            return new ClientModel(in);
//        }
//
//        @Override
//        public ClientModel[] newArray(int size) {
//            return new ClientModel[size];
//        }
//    };
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(phone);
//        dest.writeString(surname);
//        dest.writeString(name);
//        dest.writeString(email);
//        dest.writeInt(cardId);
//        dest.writeInt(authCode);
//    }
}
