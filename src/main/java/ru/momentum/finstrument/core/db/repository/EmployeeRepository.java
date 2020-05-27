package ru.momentum.finstrument.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.core.entity.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}
