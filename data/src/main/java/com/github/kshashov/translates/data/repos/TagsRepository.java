package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends JpaRepository<Tag, Long>, BaseRepo {
}
