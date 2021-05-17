package org.hse.bonusokapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardModel implements Parcelable {

    private int Id;
    private int BonusQuantity;
    private Date StartDate;
    private Date EndDate;
    private int CardCode;

    private ClientModel Client;

    public CardModel(int id, int bonusQuantity, Date startDate, Date endDate, int cardCode, ClientModel client) {
        Id = id;
        BonusQuantity = bonusQuantity;
        StartDate = startDate;
        EndDate = endDate;
        CardCode = cardCode;
        Client = client;
    }

    // Getters
    public int getId() {
        return Id;
    }

    public int getBonusQuantity() {
        return BonusQuantity;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public int getCardCode() {
        return CardCode;
    }

    public ClientModel getClient() {
        return Client;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected CardModel(Parcel in) {
        Id = in.readInt();
        BonusQuantity = in.readInt();
        CardCode = in.readInt();
        Client = in.readParcelable(ClientModel.class.getClassLoader());
    }

    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
        @Override
        public CardModel createFromParcel(Parcel in) {
            return new CardModel(in);
        }

        @Override
        public CardModel[] newArray(int size) {
            return new CardModel[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(BonusQuantity);
        dest.writeInt(CardCode);
        dest.writeParcelable(Client, flags);
    }
}
