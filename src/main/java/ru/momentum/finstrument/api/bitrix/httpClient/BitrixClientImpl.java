package ru.momentum.finstrument.api.bitrix.httpClient;

import org.apache.http.NameValuePair;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.exceptions.BitrixApiException;
import ru.momentum.finstrument.api.bitrix.exceptions.UnauthorizedBitrixApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link BitrixClient} interface.
 */
public class BitrixClientImpl implements BitrixClient {
    public static final String URL_FORMAT = "%s%s.json?auth=%s";
    public static final String URL_OAUTH_FORMAT = "https://oauth.bitrix.info/oauth/token/?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s";

    private final BitrixHttpClient httpClient;
    private final Credentials credentials;

    /**
     * Creates {@link BitrixClientImpl} instance.
     *
     * @param httpClient  {@link BitrixHttpClient} instance for connecting to site.
     * @param credentials {@link Credentials} instance with tokens and endpoint.
     */
    public BitrixClientImpl(BitrixHttpClient httpClient, Credentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    /**
     * Gets URL connecting to site.
     *
     * @param method Executing method that need to request
     * @return request URL string
     */
    private String getUrl(String method) {
        return String.format(URL_FORMAT, credentials.getClientEndpoint(), method, credentials.getAccessToken());
    }

    /**
     * Gets URL connecting to site for OAuth.
     *
     * @param client_id     Bitrix's client_id from application.
     * @param client_secret Bitrix's client_secret from application.
     * @param code          Code, that's gets from Bitrix.
     * @return request URL string
     */
    private String getOAuthUrl(String client_id, String client_secret, String code) {
        return String.format(URL_OAUTH_FORMAT, client_id, client_secret, code);
    }

    /**
     * Executes query to bitrix using method and params.
     *
     * @param method Executing method that need to request
     * @param params HTTP parameters.
     * @return JSON message, that's gets server by query.
     * @throws BitrixApiException Throws when unauthorized access.
     */
    @Override
    public String execute(String method, List<NameValuePair> params) throws BitrixApiException {
        try {
            return httpClient.post(getUrl(method), params);
        } catch (UnauthorizedBitrixApiException e) {
            return null;
        }
    }

    /**
     * Executes query to bitrix for authentication.
     *
     * @param client_id     Bitrix's client_id from application.
     * @param client_secret Bitrix's client_secret from application.
     * @param code          Code, that's gets from Bitrix.
     * @return JSON message, that's gets server by query.
     * @throws BitrixApiException Throws when unauthorized access.
     */
    @Override
    public String executeOAuth(String client_id, String client_secret, String code) throws BitrixApiException {
        try {
            return httpClient.post(getOAuthUrl(client_id, client_secret, code), new ArrayList<>());
        } catch (UnauthorizedBitrixApiException e) {
            return null;
        }
    }
}
