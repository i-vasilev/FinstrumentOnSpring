package ru.momentum.finstrument.api.bitrix.httpClient;

import org.apache.http.NameValuePair;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.exceptions.UnauthorizedBitrixApiException;

import java.util.ArrayList;
import java.util.List;

public class BitrixClientImpl implements BitrixClient {
    public static final String URL_FORMAT = "%s%s.json?auth=%s";
    public static final String URL_OAUTH_FORMAT = "https://oauth.bitrix.info/oauth/token/?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s";

    private final BitrixHttpClient httpClient;
    private final Credentials credentials;

    public BitrixClientImpl(BitrixHttpClient httpClient, Credentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    private String getUrl(String domain, String method, String accessToken) {
        return String.format(URL_FORMAT, domain, method, accessToken);
    }

    private String getOAuthUrl(String client_id, String client_secret, String code) {
        return String.format(URL_OAUTH_FORMAT, client_id, client_secret, code);
    }

    @Override
    public String execute(String method, List<NameValuePair> params) throws BitrixApiException {
        try {
            return httpClient.post(getUrl(credentials.getClientEndpoint(), method, credentials.getAccessToken()), params);
        } catch (UnauthorizedBitrixApiException e) {
            return null;
        }
    }

    @Override
    public String executeOAuth(String client_id, String client_secret, String code) throws BitrixApiException {
        try {
            return httpClient.post(getOAuthUrl(client_id, client_secret, code), new ArrayList<>());
        } catch (UnauthorizedBitrixApiException e) {
            return null;
        }
    }
}
