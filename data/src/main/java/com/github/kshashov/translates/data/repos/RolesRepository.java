package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long>, BaseRepo {
    Role findOneByCode(String code);

    @EntityGraph("Role.permissions")
    List<Role> findAll();
}
