package ru.momentum.finstrument.api.bitrix.httpClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.momentum.finstrument.api.bitrix.exceptions.BitrixApiHttpException;
import ru.momentum.finstrument.api.bitrix.exceptions.UnauthorizedBitrixApiException;
import ru.momentum.finstrument.api.bitrix.response.BitrixResponse;
import ru.momentum.finstrument.api.bitrix.response.BitrixResponseHandler;
import ru.momentum.finstrument.api.bitrix.response.ErrorBitrixResponse;
import ru.momentum.finstrument.api.bitrix.response.SuccessBitrixResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class BitrixHttpClient {
    public static final Logger logger = LoggerFactory.getLogger(BitrixHttpClient.class);

    public static final int TIMEOUT = 30000;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private final boolean logResponse;

    public BitrixHttpClient() {
        this(false);
    }

    public BitrixHttpClient(boolean logResponse) {
        this.logResponse = logResponse;
    }

    private String execute(Request request) throws BitrixApiException {
        try {
            final BitrixResponse response = request.socketTimeout(TIMEOUT).connectTimeout(TIMEOUT).execute().handleResponse(new BitrixResponseHandler(logResponse));
            if (response instanceof SuccessBitrixResponse) {
                return ((SuccessBitrixResponse) response).getResult();
            } else if (response instanceof ErrorBitrixResponse) {
                if (((ErrorBitrixResponse) response).getStatus() == 401 || ((ErrorBitrixResponse) response).getStatus() == 403) {
                    throw new UnauthorizedBitrixApiException(((ErrorBitrixResponse) response).getBody(), ((ErrorBitrixResponse) response).getStatus());
                } else {
                    throw new BitrixApiHttpException(((ErrorBitrixResponse) response).getBody(), ((ErrorBitrixResponse) response).getStatus());
                }
            } else {
                throw new BitrixApiException("Unkown response type: " + response.getClass());
            }
        } catch (IOException e) {
            throw new BitrixApiException(e);
        }
    }

    public String post(String url, List<NameValuePair> params) throws BitrixApiException {
        logger.info("post {} with {}", url, params.stream().filter(p -> !p.getName().contains("fileData")).collect(Collectors.toList()));
        return execute(Request.Post(url).bodyForm(params, UTF_8));
    }

    public String post(String url, JSONObject params) throws BitrixApiException {
        logger.info("post {} with {}", url, params.toString());
        return execute(Request.Post(url).bodyString(params.toString(), ContentType.APPLICATION_JSON));
    }

    public String get(String url) throws BitrixApiException {
        try {
            return execute(Request.Get(url));
        } catch (BitrixApiHttpException e) {
            if (e.getStatus() == 400) {
                throw new UnauthorizedBitrixApiException(e.getMessage(), e.getStatus());
            } else {
                throw e;
            }
        }
    }
}
