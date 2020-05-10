package ru.momentum.finstrument.api.bitrix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.momentum.finstrument.api.Api;
import ru.momentum.finstrument.api.bitrix.credentials.AuthCredentials;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.data.ListDeals;
import ru.momentum.finstrument.api.bitrix.data.ListDepartments;
import ru.momentum.finstrument.api.bitrix.data.ListUsers;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixApiException;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixClientImpl;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixHttpClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component
public class BitrixApi implements Api {
    private Credentials credentials;
    private BitrixHttpClient bitrixHttpClient;

    public BitrixApi() {
        bitrixHttpClient = new BitrixHttpClient();
    }

    @Override
    public ListDepartments loadListDepartments() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.execute("department.get", new ArrayList<>());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ListDepartments listDepartments = gson.fromJson(result, ListDepartments.class);
        return listDepartments;
    }

    @Override
    public ListUsers loadListUsers() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.execute("user.get", new ArrayList<>());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        ListUsers listUsers = gson.fromJson(result, ListUsers.class);
        return listUsers;
    }

    @Override
    public ListDeals loadListDeals() throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = getSimpleDateFormat(c);
        final List<NameValuePair> params = Collections.singletonList(new BasicNameValuePair("filter[>DATE_CREATE]", sdf.format(c.getTime())));
        String result = bitrixClient.execute("crm.deal.list", params);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(result, ListDeals.class);
    }

    @Override
    public Credentials loadAuthToken(String client_id, String client_secret, String code) throws BitrixApiException {
        BitrixClientImpl bitrixClient = new BitrixClientImpl(bitrixHttpClient, credentials);
        String result = bitrixClient.executeOAuth(client_id, client_secret, code);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
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
