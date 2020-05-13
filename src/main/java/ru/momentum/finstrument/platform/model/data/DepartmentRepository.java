package ru.momentum.finstrument.platform.model.data;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.platform.model.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

}
