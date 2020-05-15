package ru.momentum.finstrument.mvc.model.data;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.mvc.model.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
