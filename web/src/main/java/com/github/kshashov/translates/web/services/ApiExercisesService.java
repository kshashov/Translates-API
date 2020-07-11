package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.web.dto.*;
import org.springframework.data.domain.Pageable;

public interface ApiExercisesService {
    Paged<Exercise> getExercises(Pageable title, String filter, Long from, Long to, Long tag);

    Paged<StatsExercise> getExercisesWithStats(Pageable pageable, Long from, Long to, Long tag);

    Exercise getExercise(Long id);

    Exercise createExercise(ExerciseInfo info);

    void updateExercise(Long id, ExerciseInfo info);

    void delete(Long id);

    ExercisesStats getStats();
}
