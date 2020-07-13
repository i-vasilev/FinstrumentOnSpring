package ru.momentum.finstrument.mvc.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.momentum.finstrument.core.db.repository.CompanyRepository;
import ru.momentum.finstrument.core.entity.Company;
import ru.momentum.finstrument.core.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Scope("session")
@RequestMapping("/myaccount")
public class PersonalCabinetController {
    private final CompanyRepository companyRepository;

    public PersonalCabinetController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/companyList")
    public String getCompanyList(HttpServletRequest request, HttpServletResponse response){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            List<Company> companies = companyRepository.findAllByUser((User) principal);
        } else {
            String username = principal.toString();
        }

        return "companyList";
    }

    @GetMapping("/addCompany")
    public String addCompany(HttpServletRequest request, HttpServletResponse response){
        return "addCompany";
    }

}
