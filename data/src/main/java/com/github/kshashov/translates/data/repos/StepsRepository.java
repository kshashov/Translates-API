package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StepsRepository extends JpaRepository<Step, Long>, JpaSpecificationExecutor<Step>, BaseRepo {
    @Query("SELECT s.exercise.id FROM Step s where s.id = :stepId")
    Long findExerciseId(@Param("stepId") Long stepId);

    List<Step> findAllByExerciseIdOrderByOrder(@Param("exerciseId") Long exerciseId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    void deleteAllByExerciseId(@Param("exerciseId") Long exerciseId);
}
