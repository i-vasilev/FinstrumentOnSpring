package ru.momentum.finstrument.api.bitrix.httpClient;

import org.apache.http.NameValuePair;
import ru.momentum.finstrument.api.bitrix.exceptions.BitrixApiException;

import java.util.List;

/**
 * Interface for bitrix client
 */
public interface BitrixClient {
    String execute(String method, List<NameValuePair> params) throws BitrixApiException;

    String executeOAuth(String client_id, String client_secret, String code) throws BitrixApiException;
}
