package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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

}
