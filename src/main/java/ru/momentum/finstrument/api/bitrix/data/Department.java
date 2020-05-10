package ru.momentum.finstrument.api.bitrix.data;

import com.google.gson.annotations.SerializedName;

public class Department {
    @SerializedName("ID")
    private final int ID;
    @SerializedName("PARENT")
    private final int parentID;
    @SerializedName("NAME")
    private final String name;

    public Department(int ID, int parentID, String name) {
        this.ID = ID;
        this.parentID = parentID;
        this.name = name;
    }

    public int getParentID() {
        return parentID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
