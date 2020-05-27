package ru.momentum.finstrument.core.entity;

import com.google.gson.annotations.SerializedName;

public class Deal {
    @SerializedName("OPPORTUNITY")
    private final double opportunity;

    public Deal(double opportunity) {
        this.opportunity = opportunity;
    }

    public double getOpportunity() {
        return opportunity;
    }
}
