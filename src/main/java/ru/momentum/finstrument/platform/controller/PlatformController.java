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
import ru.momentum.finstrument.platform.model.data.CompanyRepository;
import ru.momentum.finstrument.platform.model.data.DepartmentRepository;
import ru.momentum.finstrument.platform.model.data.UserRepository;
import ru.momentum.finstrument.platform.model.entity.Company;
import ru.momentum.finstrument.platform.model.entity.Department;
import ru.momentum.finstrument.platform.model.entity.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;


    public PlatformController(CompanyRepository companyRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ApiConfiguration.class);
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;

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
                Company company = new Company(domain);
                final ListDeals listDeals = client.loadListDeals();
                final ListDepartments listDepartments = client.loadListDepartments();
                final ListUsers listUsers = client.loadListUsers();

                processingCompany(company, listDepartments, listUsers);
            }
        } catch (BitrixApiException e) {
            e.printStackTrace();
        }
        return GET_ACCESS_KEYS;
    }

    private void processingCompany(Company company, ListDepartments listDepartments, ListUsers listUsers) {
        if (companyRepository.findByAddress(company.getAddress()) == null) {
            company = companyRepository.save(company);
            Map<Integer, Integer> fids = new HashMap<>();
            for (Department department : listDepartments.getDepartments()) {
                department.setCompanyId(company.getId());
                int depFID = fids.getOrDefault(department.getParentID(), 0);
                int bId = department.getId();
                department.setParentID(depFID);
                department = departmentRepository.save(department);
                fids.putIfAbsent(bId, department.getFid());
            }
            for (User user :
                    listUsers.getUsers()) {
                user.setDepartment(fids.get(user.getDepartment()));
                user = userRepository.save(user);
            }
        }
    }
}
