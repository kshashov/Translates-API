package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.*;
import com.github.kshashov.translates.web.services.ApiExercisesService;
import com.github.kshashov.translates.web.services.ApiStepsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExercisesController {
    private final ApiExercisesService exercisesService;
    private final ApiStepsService stepsService;

    @Autowired
    public ExercisesController(ApiExercisesService exercisesService, ApiStepsService stepsService) {
        this.exercisesService = exercisesService;
        this.stepsService = stepsService;
    }

    @GetMapping
    public Paged<Exercise> getExercises(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "from", required = false) Long from,
            @RequestParam(value = "to", required = false) Long to,
            @RequestParam(value = "tag", required = false) Long tag
    ) {
        if (StringUtils.isBlank(sort)) {
            sort = "title";
        }
        if (StringUtils.isBlank(direction)) {
            direction = "asc";
        }
        if (size < 1) {
            size = Integer.MAX_VALUE;
        }

        return exercisesService.getExercises(
                PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)),
                filter, from, to, tag);
    }

    @GetMapping("user")
    public Paged<StatsExercise> getStatsExercises(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "from", required = false) Long from,
            @RequestParam(value = "to", required = false) Long to,
            @RequestParam(value = "tag", required = false) Long tag
    ) {
        if (StringUtils.isBlank(sort)) {
            sort = "title";
        }
        if (StringUtils.isBlank(direction)) {
            direction = "asc";
        }
        if (size < 1) {
            size = Integer.MAX_VALUE;
        }

        return exercisesService.getExercisesWithStats(
                PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)), from, to, tag);
    }

    @PostMapping
    @SecurityRequirement(name = "bearer")
    public Exercise createExercise(@RequestBody ExerciseInfo info) {
        return exercisesService.createExercise(info);
    }

    @GetMapping("/{id}")
    public Exercise getExercise(@PathVariable("id") Long id) {
        return exercisesService.getExercise(id);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createExercise(@PathVariable("id") Long id, @RequestBody ExerciseInfo info) {
        exercisesService.updateExercise(id, info);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteExercise(@PathVariable("id") Long id) {
        exercisesService.delete(id);
    }

    @GetMapping("/{id}/steps")
    public List<Step> getExerciseSteps(@PathVariable("id") Long id) {
        return stepsService.getByExercise(id);
    }

    @PostMapping("/{id}/steps")
    @SecurityRequirement(name = "bearer")
    public List<Step> replaceExerciseSteps(@PathVariable("id") Long id, @RequestBody List<StepInfo> info) {
        return stepsService.replaceExerciseSteps(id, info);
    }

    @GetMapping("/stats")
    public ExercisesStats getExercisesStats() {
        return exercisesService.getStats();
    }
}
