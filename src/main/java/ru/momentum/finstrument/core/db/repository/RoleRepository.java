package ru.momentum.finstrument.core.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.momentum.finstrument.core.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
