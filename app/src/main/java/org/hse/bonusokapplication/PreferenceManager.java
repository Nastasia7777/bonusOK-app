package org.hse.bonusokapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;

public class PreferenceManager{
    private final static String PREFERENCE_FILE = "org.hse.android.file";
    private final static String TOKEN = "token";
    private final static String PHONE_NUMBER = "phone";
    private final static String CLIENT = "client";
    private final static String CARD = "card";

    private final SharedPreferences sharedPref;

    public PreferenceManager(Context context){
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE);
    }

    public void deleteAllPreferences(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public void saveToken(String value) {
        saveValue(TOKEN, value);
    }

    public String getToken(){
        return getValue(TOKEN, "");
    }

    public void savePhoneNumber(String value) {
        saveValue(PHONE_NUMBER, value);
    }

    public String getPhoneNumber(){
        return getValue(PHONE_NUMBER, "");
    }

    public void saveClientModel(Object object) {
        saveModel(CLIENT, object);
    }

    public ClientModel getClientModel(){
        return getClient(CLIENT, "");
    }

    public void saveCardModel(Object object) {
        saveModel(CARD, object);
    }

    public CardModel getCardModel(){
        return getCard(CARD, "");
    }

    private void saveValue(String key, String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getValue(String key, String defaultValue) {
        return sharedPref.getString(key, defaultValue);
    }

    private void saveModel(String key, Object object) {
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        prefsEditor.putString(key, json);
        prefsEditor.commit();
    }

    private ClientModel getClient(String key, String defaultValue) {
        Gson gson = new Gson();
        String json = sharedPref.getString(key, defaultValue);
        return gson.fromJson(json, ClientModel.class);
    }

    private CardModel getCard(String key, String defaultValue) {
        Gson gson = new Gson();
        String json = sharedPref.getString(key, defaultValue);
        return gson.fromJson(json, CardModel.class);
    }
}
