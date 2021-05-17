package org.hse.bonusokapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ClientModel implements Parcelable {

    private int Id;
    private String Phone;
    private String Surname;
    private String Name;
    private String Email;
    private Date Birthdate;

    private int CardId;
    private int AuthCode;
    private Date AuthCodeDeathTime;

    private CardModel Card;

    public ClientModel(int id, String phone, String surname, String name, String email, Date birthdate, int cardId, int authCode, Date authCodeDeathTime, CardModel card) {
        Id = id;
        Phone = phone;
        Surname = surname;
        Name = name;
        Email = email;
        Birthdate = birthdate;
        CardId = cardId;
        AuthCode = authCode;
        AuthCodeDeathTime = authCodeDeathTime;
        Card = card;
    }

    // Getters
    public int getId() {
        return Id;
    }

    public String getPhone() {
        return Phone;
    }

    public String getSurname() {
        return Surname;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public Date getBirthdate() {
        return Birthdate;
    }

    public int getCardId() {
        return CardId;
    }

    public int getAuthCode() {
        return AuthCode;
    }

    public Date getAuthCodeDeathTime() {
        return AuthCodeDeathTime;
    }

    public CardModel getCard() {
        return Card;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected ClientModel(Parcel in) {
        Id = in.readInt();
        Phone = in.readString();
        Surname = in.readString();
        Name = in.readString();
        Email = in.readString();
        CardId = in.readInt();
        AuthCode = in.readInt();
    }

    public static final Creator<ClientModel> CREATOR = new Creator<ClientModel>() {
        @Override
        public ClientModel createFromParcel(Parcel in) {
            return new ClientModel(in);
        }

        @Override
        public ClientModel[] newArray(int size) {
            return new ClientModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Phone);
        dest.writeString(Surname);
        dest.writeString(Name);
        dest.writeString(Email);
        dest.writeInt(CardId);
        dest.writeInt(AuthCode);
    }
}
