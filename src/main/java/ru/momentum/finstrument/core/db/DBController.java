package ru.momentum.finstrument.core.db;

import ru.momentum.finstrument.core.entity.ListDepartments;
import ru.momentum.finstrument.core.entity.ListEmployees;
import ru.momentum.finstrument.core.db.repository.CompanyRepository;
import ru.momentum.finstrument.core.db.repository.DepartmentRepository;
import ru.momentum.finstrument.core.db.repository.EmployeeRepository;
import ru.momentum.finstrument.core.entity.Company;
import ru.momentum.finstrument.core.entity.Department;
import ru.momentum.finstrument.core.entity.Employee;

import java.util.HashMap;
import java.util.Map;

public class DBController {
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DBController(CompanyRepository companyRepository, DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }


    public void processingCompany(Company company, ListDepartments listDepartments, ListEmployees listEmployees) {
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
            for (Employee employee :
                    listEmployees.getEmployees()) {
                employee.setDepartment(fids.get(employee.getDepartment()));
                employee = employeeRepository.save(employee);
            }
        }
    }
}
