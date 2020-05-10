package ru.momentum.finstrument.api.bitrix.response;

public class SuccessBitrixResponse implements BitrixResponse {
    private final String result;

    public SuccessBitrixResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;// result;
    }
}
