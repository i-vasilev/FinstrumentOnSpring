package ru.momentum.finstrument.api.bitrix.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("ID")
    private int ID;
    @SerializedName("DEPARTMENT")
    private List<Integer> department;
    @SerializedName("ACTIVE")
    private boolean isActive;

    public User(int ID, List<Integer> department, boolean isActive) {
        this.ID = ID;
        this.department = department;
        this.isActive = isActive;
    }

    public int getID() {
        return ID;
    }

    public List<Integer> getDepartments() {
        return department;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isActive() {
        return isActive;
    }
}
