package ru.momentum.finstrument.rest.controller;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.momentum.finstrument.core.entity.ListDeals;
import ru.momentum.finstrument.core.entity.ListDepartments;
import ru.momentum.finstrument.core.entity.ListEmployees;
import ru.momentum.finstrument.core.db.DBController;
import ru.momentum.finstrument.core.entity.Company;
import ru.momentum.finstrument.rest.responce.MessageResponce;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest")
public class RestServiceController {
    @Autowired
    DBController dbController;

    @PostMapping("/addDepartmentsAndEmployees")
    public MessageResponce addDepartments(HttpServletRequest request) {
        final String departments = request.getHeader("departments");
        final String employees = request.getHeader("employees");
        final String companyString = request.getHeader("company");
        final GsonBuilder gsonBuilder = new GsonBuilder();
        ListEmployees employeesList = gsonBuilder.create().fromJson(employees, ListEmployees.class);
        ListDepartments listDepartments = gsonBuilder.create().fromJson(departments, ListDepartments.class);
        Company company = new Company(companyString);
        dbController.processingCompany(company, listDepartments, employeesList);
        return new MessageResponce("OK");
    }

    @PostMapping("/addDeals")
    public MessageResponce addDeals(HttpServletRequest request) {
        final String deals = request.getHeader("deals");
        final GsonBuilder gsonBuilder = new GsonBuilder();
        ListDeals listDepartments = gsonBuilder.create().fromJson(deals, ListDeals.class);
        return new MessageResponce("OK");
    }
}
