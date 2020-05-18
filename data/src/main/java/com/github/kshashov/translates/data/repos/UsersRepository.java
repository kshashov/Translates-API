package com.github.kshashov.translates.data.repos;


import com.github.kshashov.translates.data.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>, BaseRepo {

    @EntityGraph("User.permissions")
    User findWithPermissionsById(Long id);

    User findOneByEmail(String email);
}
