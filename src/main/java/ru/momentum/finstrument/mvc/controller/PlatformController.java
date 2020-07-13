package ru.momentum.finstrument.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.momentum.finstrument.api.Api;
import ru.momentum.finstrument.api.ApiConfiguration;
import ru.momentum.finstrument.api.bitrix.BitrixApi;
import ru.momentum.finstrument.core.entity.ListDeals;
import ru.momentum.finstrument.core.entity.ListDepartments;
import ru.momentum.finstrument.core.entity.ListEmployees;
import ru.momentum.finstrument.api.bitrix.exceptions.BitrixApiException;
import ru.momentum.finstrument.core.db.DBController;
import ru.momentum.finstrument.core.entity.Company;

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
    public static final String ADD_COMPANY = "addCompany";

    private final AnnotationConfigApplicationContext annotationConfigApplicationContext;

    @Autowired
    private final DBController dbController;

    public PlatformController(DBController dbController) {
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApiConfiguration.class);
        this.dbController = dbController;
    }

    private String getCodeUrl(String domain, String appId) {
        return String.format(CODE_URL_FORMAT, domain, appId);
    }

    @GetMapping("/")
    @PostMapping("/")
    public String indexPage() {
        return INDEX;
    }

    @GetMapping("/addCompany")
    public String addCompanyPage() {
        return ADD_COMPANY;
    }
    @PostMapping("/addCompany")
    public String addCompanyPostPage() {
        return ADD_COMPANY;
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
                Company company = new Company(domain);
                final ListDeals listDeals = client.loadListDeals();
                final ListDepartments listDepartments = client.loadListDepartments();
                final ListEmployees listEmployees = client.loadListEmployees();

                dbController.processingCompany(company, listDepartments, listEmployees);
            }
        } catch (BitrixApiException e) {
            e.printStackTrace();
        }
        return GET_ACCESS_KEYS;
    }
}
