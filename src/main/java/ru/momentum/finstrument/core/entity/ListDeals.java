package ru.momentum.finstrument.core.entity;

import com.google.gson.annotations.SerializedName;
import ru.momentum.finstrument.core.entity.Deal;

import java.util.Iterator;
import java.util.List;

public class ListDeals {
    @SerializedName("result")
    private final List<Deal> deals;

    public ListDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public List<Deal> getDeals() {
        return deals;
    }

    public double getOpportunity(){
        double result = 0;
        final Iterator<Deal> dealIterator = deals.iterator();
        while (dealIterator.hasNext()) {
            result += dealIterator.next().getOpportunity();
        }
        return result;
    }
}
