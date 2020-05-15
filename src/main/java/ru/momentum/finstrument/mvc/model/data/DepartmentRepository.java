package ru.momentum.finstrument.mvc.model.data;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.mvc.model.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
