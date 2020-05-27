package ru.momentum.finstrument.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.momentum.finstrument.core.db.DBController;
import ru.momentum.finstrument.core.db.repository.CompanyRepository;
import ru.momentum.finstrument.core.db.repository.DepartmentRepository;
import ru.momentum.finstrument.core.db.repository.EmployeeRepository;

@Configuration
@ComponentScan
public class CoreConfiguration {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public CoreConfiguration(CompanyRepository companyRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public DBController getDBController() {
        return new DBController(companyRepository, departmentRepository, employeeRepository);
    }
}
