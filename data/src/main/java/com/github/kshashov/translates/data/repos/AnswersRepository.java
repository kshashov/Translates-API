package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answer, Long>, BaseRepo {
    List<Answer> findAllByStepExerciseId(@Param("exerciseId") Long exerciseId);
}
