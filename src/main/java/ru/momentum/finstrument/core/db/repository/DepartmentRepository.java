package ru.momentum.finstrument.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.core.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
