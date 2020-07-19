package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.common.errors.NotFoundException;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.StepsRepository;
import com.github.kshashov.translates.data.services.StepsService;
import com.github.kshashov.translates.web.dto.Step;
import com.github.kshashov.translates.web.dto.StepInfo;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ApiStepsServiceImpl implements ApiStepsService {
    private final StepsService stepsService;
    private final ExercisesRepository exercisesRepository;
    private final StepsRepository stepsRepository;
    private final DtoMapper mapper;

    public ApiStepsServiceImpl(StepsService stepsService, ExercisesRepository exercisesRepository, StepsRepository stepsRepository, DtoMapper mapper) {
        this.stepsService = stepsService;
        this.exercisesRepository = exercisesRepository;
        this.stepsRepository = stepsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Step> getByExercise(Long exerciseId) {
        return mapper.toStep(stepsRepository.findAllByExerciseIdOrderByOrder(exerciseId));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_EXERCISES.getCode())")
    public List<Step> replaceExerciseSteps(Long exerciseId, List<StepInfo> info) {
        Objects.requireNonNull(exerciseId);
        Objects.requireNonNull(info);

        if (!exercisesRepository.existsById(exerciseId)) {
            throw new NotFoundException("Exercise is not found");
        }

        if (info.isEmpty()) {
            throw new BadRequestException("Steps list is empty");
        }

        List<StepsService.StepInfo> steps = mapper.toStepInfo(info);

        return mapper.toStep(stepsService.replaceExerciseSteps(exerciseId, steps));
    }
}
