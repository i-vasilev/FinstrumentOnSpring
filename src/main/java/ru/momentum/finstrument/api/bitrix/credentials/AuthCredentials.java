package ru.momentum.finstrument.api.bitrix.credentials;

import com.google.gson.annotations.SerializedName;

public class AuthCredentials implements Credentials {
    @SerializedName("access_token")
    private final String access_token;
    @SerializedName("refresh_token")
    private final String refreshToken;
    @SerializedName("client_endpoint")
    private final String clientEndpoint;

    public AuthCredentials(String access_token, String refreshToken, String clientEndpoint) {
        this.access_token = access_token;
        this.refreshToken = refreshToken;
        this.clientEndpoint = clientEndpoint;
    }

    @Override
    public String getAccessToken() {
        return access_token;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    @Override
    public String getClientEndpoint() {
        return clientEndpoint;
    }
}
