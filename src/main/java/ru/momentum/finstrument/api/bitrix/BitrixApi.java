package ru.momentum.finstrument.api.bitrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;
import ru.momentum.finstrument.api.Api;
import ru.momentum.finstrument.api.bitrix.credentials.AuthCredentials;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.data.ListDeals;
import ru.momentum.finstrument.api.bitrix.data.ListDepartments;
import ru.momentum.finstrument.api.bitrix.data.ListUsers;
import ru.momentum.finstrument.api.bitrix.deserializer.UserDeserializer;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixApiException;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixClientImpl;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixHttpClient;
import ru.momentum.finstrument.mvc.model.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component
public class BitrixApi implements Api {
    private Credentials credentials;
    private BitrixHttpClient bitrixHttpClient;
    private Gson gson;

    public BitrixApi() {
        bitrixHttpClient = new BitrixHttpClient();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(User.class, new UserDeserializer());
        gson = builder.create();
    }

    @Override
    public ListDepartments loadListDepartments() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.execute("department.get", new ArrayList<>());

        return gson.fromJson(result, ListDepartments.class);
    }

    @Override
    public ListUsers loadListUsers() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.execute("user.get", new ArrayList<>());

        return gson.fromJson(result, ListUsers.class);
    }

    @Override
    public ListDeals loadListDeals() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = getSimpleDateFormat(c);
        final List<NameValuePair> params = Collections.singletonList(new BasicNameValuePair("filter[>DATE_CREATE]", sdf.format(c.getTime())));
        String result = bitrixClient.execute("crm.deal.list", params);

        return gson.fromJson(result, ListDeals.class);
    }

    @Override
    public Credentials loadAuthToken(String client_id, String client_secret, String code) throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.executeOAuth(client_id, client_secret, code);

        credentials = gson.fromJson(result, AuthCredentials.class);
        return credentials;
    }

    private SimpleDateFormat getSimpleDateFormat(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) / 3);
        return new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
    }
}
