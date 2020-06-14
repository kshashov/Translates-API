package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionsRepository extends JpaRepository<Permission, Long>, BaseRepo {
    Optional<Permission> findOneByCode(String code);
}
