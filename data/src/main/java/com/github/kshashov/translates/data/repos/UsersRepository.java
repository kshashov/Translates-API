package com.github.kshashov.translates.data.repos;


import com.github.kshashov.translates.data.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, BaseRepo {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> findByEmailContainingIgnoreCase(Pageable pageable, @Param("email") String email);

    @EntityGraph("User.permissions")
    Optional<User> findWithPermissionsById(Long id);

    Optional<User> findOneByClientAndSub(@Param("client") String client, @Param("sub") String sub);
}
