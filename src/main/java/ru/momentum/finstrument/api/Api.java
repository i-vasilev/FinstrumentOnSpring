package ru.momentum.finstrument.api;

import org.springframework.stereotype.Component;
import ru.momentum.finstrument.api.bitrix.credentials.Credentials;
import ru.momentum.finstrument.api.bitrix.exceptions.BitrixApiException;
import ru.momentum.finstrument.core.entity.ListDeals;
import ru.momentum.finstrument.core.entity.ListDepartments;
import ru.momentum.finstrument.core.entity.ListEmployees;

@Component
public interface Api {
    ListDepartments loadListDepartments() throws BitrixApiException;

    ListEmployees loadListEmployees() throws BitrixApiException;

    ListDeals loadListDeals() throws BitrixApiException;

    Credentials loadAuthToken(String client_id, String client_secret, String code) throws BitrixApiException;
}
