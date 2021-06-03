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
    private Date birthday;
    private int cardId;

    public ClientModel(int id, String phone, String surname, String name, String email, Date birthday, int cardId) {
        this.id = id;
        this.phone = phone;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.cardId = cardId;
    }

    public ClientModel() { }

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
        return birthday;
    }

    public int getCardId() {
        return cardId;
    }
}
