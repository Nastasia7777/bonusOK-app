package org.hse.bonusokapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class PromoModel{

    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<ClientModel> clients;

    // Constructor
    public PromoModel(int id, String name, String description, Date startDate, Date endDate, List<ClientModel> clients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clients = clients;
    }

//    protected PromoModel(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//        description = in.readString();
//    }
//
//    public static final Creator<PromoModel> CREATOR = new Creator<PromoModel>() {
//        @Override
//        public PromoModel createFromParcel(Parcel in) {
//            return new PromoModel(in);
//        }
//
//        @Override
//        public PromoModel[] newArray(int size) {
//            return new PromoModel[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeString(description);
//    }
//
    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<ClientModel> getClients(){
        return clients;
    }
}
