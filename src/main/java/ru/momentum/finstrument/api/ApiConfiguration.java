package ru.momentum.finstrument.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.momentum.finstrument.api.bitrix.BitrixApi;


@Configuration
@ComponentScan
public class ApiConfiguration {
    public BitrixApi getBitrixApi() {
        return new BitrixApi();
    }
}
