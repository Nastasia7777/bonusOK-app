package org.hse.bonusokapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PromoModel implements Parcelable {

    private int Id;
    private String Name;
    private String Description;
    private Date StartDate;
    private Date EndDate;

    // Constructor
    public PromoModel(int id, String name, String description, Date startDate, Date endDate) {
        Id = id;
        Name = name;
        Description = description;
        StartDate = startDate;
        EndDate = endDate;
    }

    protected PromoModel(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        Description = in.readString();
    }

    public static final Creator<PromoModel> CREATOR = new Creator<PromoModel>() {
        @Override
        public PromoModel createFromParcel(Parcel in) {
            return new PromoModel(in);
        }

        @Override
        public PromoModel[] newArray(int size) {
            return new PromoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeString(Description);
    }

    // Getters
    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }
}
