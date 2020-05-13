package ru.momentum.finstrument.platform.model.data;

import org.springframework.data.repository.CrudRepository;
import ru.momentum.finstrument.platform.model.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
