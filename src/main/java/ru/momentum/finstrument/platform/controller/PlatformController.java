package ru.momentum.finstrument.platform.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.momentum.finstrument.api.Api;
import ru.momentum.finstrument.api.ApiConfiguration;
import ru.momentum.finstrument.api.bitrix.BitrixApi;
import ru.momentum.finstrument.api.bitrix.data.ListDeals;
import ru.momentum.finstrument.api.bitrix.data.ListDepartments;
import ru.momentum.finstrument.api.bitrix.data.ListUsers;
import ru.momentum.finstrument.api.bitrix.httpClient.BitrixApiException;

import javax.servlet.http.HttpSession;

@Controller
@Scope("session")
public class PlatformController {
    public static final String CODE_URL_FORMAT = "https://%s.bitrix24.ru/oauth/authorize/?client_id=%s";
    public static final String DOMAIN = "domain";
    public static final String APP_ID = "appId";
    public static final String CLIENT_SECRET = "clientSecret";
    public static final String INDEX = "index";
    public static final String CODE = "code";
    public static final String GET_ACCESS_KEYS = "getAccessKeys";
    public static final String REDIRECT = "redirect:";

    private final AnnotationConfigApplicationContext annotationConfigApplicationContext;

    public PlatformController() {
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApiConfiguration.class);
    }

    private String getCodeUrl(String domain, String appId) {
        return String.format(CODE_URL_FORMAT, domain, appId);
    }

    @GetMapping("/")
    public String indexPage() {
        return INDEX;
    }

    @PostMapping("/getCode")
    public String getCode(@RequestParam(DOMAIN) String domain, @RequestParam(APP_ID) String appId,
                          @RequestParam(CLIENT_SECRET) String clientSecret, HttpSession httpSession) {
        if (domain.equals("") || appId.equals("") || clientSecret.equals("")) {
            return REDIRECT + "/";
        }
        httpSession.setAttribute(APP_ID, appId);
        httpSession.setAttribute(CLIENT_SECRET, clientSecret);
        return REDIRECT + getCodeUrl(domain, appId);
    }

    @GetMapping("/getKeys")
    public String getKeys(@RequestParam(CODE) String code, @RequestParam(DOMAIN) String domain, HttpSession httpSession) {
        try {
            final String appId = httpSession.getAttribute(APP_ID).toString();
            final String clientSecret = httpSession.getAttribute(CLIENT_SECRET).toString();
            if (appId == null || clientSecret == null) {
                return INDEX;
            } else {
                final Api client = annotationConfigApplicationContext.getBean(BitrixApi.class);
                client.loadAuthToken(appId, clientSecret, code);
                final ListDeals listDeals = client.loadListDeals();
                final ListUsers listUsers = client.loadListUsers();
                final ListDepartments listDepartments = client.loadListDepartments();
            }
        } catch (BitrixApiException e) {
            e.printStackTrace();
        }
        return GET_ACCESS_KEYS;
    }
}
