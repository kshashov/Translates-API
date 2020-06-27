package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.common.errors.NotFoundException;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.StepsRepository;
import com.github.kshashov.translates.data.services.StepsService;
import com.github.kshashov.translates.web.dto.Step;
import com.github.kshashov.translates.web.dto.StepInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ApiStepsServiceImpl implements ApiStepsService {
    private final StepsService stepsService;
    private final ExercisesRepository exercisesRepository;
    private final StepsRepository stepsRepository;

    public ApiStepsServiceImpl(StepsService stepsService, ExercisesRepository exercisesRepository, StepsRepository stepsRepository) {
        this.stepsService = stepsService;
        this.exercisesRepository = exercisesRepository;
        this.stepsRepository = stepsRepository;
    }

    @Override
    public List<Step> getByExercise(Long exerciseId) {
        return stepsRepository.findAllByExerciseIdOrderByOrder(exerciseId).stream()
                .map(Step::of)
                .collect(Collectors.toList());
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

        List<StepsService.StepInfo> steps = info.stream().map(i -> {
            StepsService.StepInfo stepInfo = new StepsService.StepInfo();

            stepInfo.setText(i.getText());

            stepInfo.setAnswers(i.getAnswers().stream().map(a -> {
                StepsService.AnswerInfo answerInfo = new StepsService.AnswerInfo();
                answerInfo.setText(a.getText());
                return answerInfo;
            }).collect(Collectors.toList()));

            stepInfo.setWords(i.getWords().stream().map(w -> {
                StepsService.WordInfo wordInfo = new StepsService.WordInfo();
                wordInfo.setSource(w.getSource());
                wordInfo.setTranslation(w.getTranslation());
                return wordInfo;
            }).collect(Collectors.toList()));

            return stepInfo;
        }).collect(Collectors.toList());

        return stepsService.replaceExerciseSteps(exerciseId, steps)
                .stream()
                .map(Step::of)
                .collect(Collectors.toList());
    }
}
