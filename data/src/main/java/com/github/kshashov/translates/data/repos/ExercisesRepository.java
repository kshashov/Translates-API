package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long>, JpaSpecificationExecutor<Exercise>, BaseRepo {
}
