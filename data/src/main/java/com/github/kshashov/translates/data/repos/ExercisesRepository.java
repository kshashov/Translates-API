package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExercisesRepository extends JpaRepository<Exercise, Long>, JpaSpecificationExecutor<Exercise>, BaseRepo {

    @Query("SELECT new com.github.kshashov.translates.data.repos.ByLangs(l.id, COUNT(e.id)) " +
            "FROM Language l LEFT JOIN Exercise e on l.id = e.from.id " +
            "GROUP BY l.id")
    List<ByLangs> countByFrom();

    @Query("SELECT new com.github.kshashov.translates.data.repos.ByLangs(l.id, COUNT(e.id)) " +
            "FROM Language l LEFT JOIN Exercise e on l.id = e.to.id " +
            "GROUP BY l.id")
    List<ByLangs> countByTo();

    @Query("SELECT new com.github.kshashov.translates.data.repos.ByTags(t.id, COUNT(e.id)) " +
            "FROM Tag t LEFT JOIN t.exercises e " +
            "GROUP BY t.id")
    List<ByTags> countByTags();

    @Query("SELECT new com.github.kshashov.translates.data.repos.UserAnswersStats(e.id, s.id, a.success) " +
            "FROM Exercise e " +
            "LEFT JOIN Step s ON s.exercise.id = e.id " +
            "LEFT JOIN UserAnswer a " +
            "   ON a.identity.userId = :userId " +
            "       AND a.step.id = s.id " +
            "       AND a.identity.createdAt IN (SELECT MAX(a.identity.createdAt) FROM UserAnswer a WHERE a.identity.userId = :userId AND a.step.id = s.id)" +
            "GROUP BY e.id, s.id, a.success " +
            "HAVING e.id in :ids")
    List<UserAnswersStats> getUserStats(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    @Query("SELECT new com.github.kshashov.translates.data.repos.StepsStats(e.id, COUNT(s.id)) " +
            "FROM Exercise e " +
            "LEFT JOIN Step s ON s.exercise.id = e.id " +
            "GROUP BY e.id " +
            "HAVING e.id in :ids")
    List<StepsStats> getStepsStats(@Param("ids") List<Long> ids);
}
