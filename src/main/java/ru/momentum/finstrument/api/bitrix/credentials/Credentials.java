package ru.momentum.finstrument.api.bitrix.credentials;

/**
 * Interface for credentials to connect to some REST API with Auth API 2.0
 */
public interface Credentials {
    /**
     * Gets access token to connecting to some REST API.
     *
     * @return access token.
     */
    String getAccessToken();

    /**
     * Gets refresh token to connecting to some REST API.
     *
     * @return refresh token.
     */
    String getRefreshToken();

    /**
     * Gets client endpoint to connecting to some REST API.
     *
     * @return address in bitrix system.
     */
    String getClientEndpoint();
}
