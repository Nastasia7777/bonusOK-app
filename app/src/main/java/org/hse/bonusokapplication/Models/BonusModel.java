package org.hse.bonusokapplication.Models;

import java.util.Date;

public class BonusModel {
    private int adminId;
    private int cardCode;
    private int bonusQuantity;

    public BonusModel(int id, int code, int bonus) {
        this.adminId = id;
        this.cardCode = code;
        this.bonusQuantity = bonus;
    }

    public BonusModel() { }

    // Getters
    public int getAdminId() {
        return adminId;
    }

    public int getCardCode() {
        return cardCode;
    }

    public int getBonusQuantity() {
        return bonusQuantity;
    }

    // Setters
    public void setAdminId(int id) {
        this.adminId = id;
    }

    public void setCardCode (int code) {
        this.cardCode = code;
    }

    public void setBonusQuantity(int bonus) {
        this.bonusQuantity = bonus;
    }
}
