package org.hse.bonusokapplication.Models;

import java.util.Date;

public class CardModel  {

    private int id;
    private int bonusQuantity;
    private Date startDate;
    private Date endDate;
    private int cardCode;


    public CardModel(int id, int bonusQuantity, Date startDate, Date endDate, int cardCode) {
        this.id = id;
        this.bonusQuantity = bonusQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cardCode = cardCode;
    }

    @Override
    public String toString() {
        return "CardModel{" +
                "Id=" + id +
                ", BonusQuantity=" + bonusQuantity +
                ", StartDate=" + startDate +
                ", EndDate=" + endDate +
                ", CardCode=" + cardCode +
                '}';
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getCardCode() {
        return cardCode;
    }

//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    protected CardModel(Parcel in) {
//        Id = in.readInt();
//        BonusQuantity = in.readInt();
//        CardCode = in.readInt();
//
//    }
//
//    public static final Creator<CardModel> CREATOR = new Creator<CardModel>() {
//        @Override
//        public CardModel createFromParcel(Parcel in) {
//            return new CardModel(in);
//        }
//
//        @Override
//        public CardModel[] newArray(int size) {
//            return new CardModel[size];
//        }
//    };
//
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(Id);
//        dest.writeInt(BonusQuantity);
//        dest.writeInt(CardCode);
//    }
}
