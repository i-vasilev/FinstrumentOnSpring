package ru.momentum.finstrument.api;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.data.ListDeals;
import ru.momentum.finstrument.api.bitrix.data.ListDepartments;
import ru.momentum.finstrument.api.bitrix.data.ListUsers;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixApiException;

@Component
public interface Api {
    ListDepartments loadListDepartments() throws BitrixApiException;
    ListUsers loadListUsers() throws BitrixApiException;
    ListDeals loadListDeals() throws BitrixApiException;
    Credentials loadAuthToken(String client_id, String client_secret, String code) throws BitrixApiException;
}
