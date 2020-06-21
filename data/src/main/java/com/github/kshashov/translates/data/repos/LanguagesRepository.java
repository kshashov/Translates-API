package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguagesRepository extends JpaRepository<Language, Long>, BaseRepo {
}
