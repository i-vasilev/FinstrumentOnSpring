package ru.momentum.finstrument.api.bitrix.response;

public class ErrorBitrixResponse implements BitrixResponse {
    private final int status;
    private final String body;

    public ErrorBitrixResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
