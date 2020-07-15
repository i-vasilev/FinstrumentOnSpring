package ru.momentum.finstrument.api.bitrix.credentials;

import com.google.gson.annotations.SerializedName;

/**
 * Credentials to connect to some REST API with Auth API 2.0.
 */
public class AuthCredentials implements Credentials {
    @SerializedName("access_token")
    private final String accessToken;
    @SerializedName("refresh_token")
    private final String refreshToken;
    @SerializedName("client_endpoint")
    private final String clientEndpoint;

    /**
     * Creates {@link AuthCredentials}'s instance with filled tokens and client endpoint (Bitrix's name).
     *
     * @param accessToken    access token to connecting to Bitrix.
     * @param refreshToken   refresh token to connecting to Bitrix.
     * @param clientEndpoint client endpoint to connecting to Bitrix.
     */
    public AuthCredentials(String accessToken, String refreshToken, String clientEndpoint) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.clientEndpoint = clientEndpoint;
    }

    /**
     * Gets access token to connecting to Bitrix.
     *
     * @return access token.
     */
    @Override
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Gets refresh token to connecting to Bitrix.
     *
     * @return refresh token.
     */
    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Gets client endpoint to connecting to Bitrix.
     *
     * @return address in bitrix system.
     */
    @Override
    public String getClientEndpoint() {
        return clientEndpoint;
    }
}
