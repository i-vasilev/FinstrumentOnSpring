package ru.momentum.finstrument.api.bitrix.credentials;

public interface Credentials {
    String getAccessToken();
    String getRefreshToken();
    String getClientEndpoint();
}
