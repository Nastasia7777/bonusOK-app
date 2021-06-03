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

    public ClientModel(int id, String phone, String surname, String name, String email, Date birthday, int cardId) {
        this.id = id;
        this.phone = phone;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthdate = birthday;
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

    public Date getBirthday() {
        return birthdate;
    }

    public int getCardId() {
        return cardId;
    }

    // Setters
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(Date date) {
        this.birthdate = date;
    }
}
