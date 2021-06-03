package org.hse.bonusokapplication.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.hse.bonusokapplication.Models.CardModel;
import org.hse.bonusokapplication.Models.ClientModel;
import org.hse.bonusokapplication.Models.DeviceModel;
import org.hse.bonusokapplication.Repositories.ClientRepository;
import org.jetbrains.annotations.NotNull;

public class ClientViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> user_is_registered;

    private ClientRepository clientRepository;
    public MutableLiveData<DeviceModel> deviceModel;
    public MutableLiveData<ClientModel> clientModel;
    public MutableLiveData<CardModel> cardModel;

    public ClientViewModel (@NotNull Application application) {
        super(application);
        clientRepository = ClientRepository.getInstance();
        user_is_registered = new MutableLiveData<>();
        deviceModel = new MutableLiveData<DeviceModel>();
        clientModel = new MutableLiveData<ClientModel>();
        cardModel = new MutableLiveData<CardModel>();
    }

    //public MutableLiveData<DeviceModel> getDeviceModel() { return deviceModel; }
    //public MutableLiveData<ClientModel> getClientModel() { return clientModel; }
    //public MutableLiveData<CardModel> getCardModel() { return cardModel; }

    /*public void getToken(String phone, String code) {
        deviceModel.postValue(clientRepository.makeTokenApiCall(phone, code));
    }
    public void getClient(int id, String token) {
        clientModel.postValue(clientRepository.makeClientApiCall(id, token));
    }
    public void getCard(int id, String token) {
        cardModel.postValue(clientRepository.makeClientCardApiCall(id, token));
    }*/
}
