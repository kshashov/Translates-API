package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.Answer;
import com.github.kshashov.translates.data.entities.Exercise;
import com.github.kshashov.translates.data.entities.Step;
import com.github.kshashov.translates.data.entities.Word;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.StepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StepsServiceImpl implements StepsService {
    private final StepsRepository stepsRepository;
    private final ExercisesRepository exercisesRepository;

    @Autowired
    public StepsServiceImpl(StepsRepository stepsRepository, ExercisesRepository exercisesRepository) {
        this.stepsRepository = stepsRepository;
        this.exercisesRepository = exercisesRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public List<Step> replaceExerciseSteps(Long exerciseId, List<StepInfo> stepsInfo) {
        Objects.requireNonNull(exerciseId);
        Objects.requireNonNull(stepsInfo);

        Exercise exercise = exercisesRepository.getOne(exerciseId);

        // Delete existing steps
        stepsRepository.deleteAllByExerciseId(exerciseId);
        stepsRepository.flush();

        // Create new steps
        List<Step> steps = new ArrayList<>(stepsInfo.size());
        for (int i = 0; i < stepsInfo.size(); i++) {
            StepInfo s = stepsInfo.get(i);
            Step step = new Step();
            step.setOrder(i);
            step.setExercise(exercise);
            step.setText(s.getText());

            step.setAnswers(s.getAnswers().stream()
                    .map(a -> {
                        Answer answer = new Answer();
                        answer.setStep(step);
                        answer.setText(a.getText());
                        return answer;
                    }).collect(Collectors.toSet()));

            step.setWords(s.getWords().stream()
                    .map(a -> {
                        Word word = new Word();
                        word.setStep(step);
                        word.setSource(a.getSource());
                        word.setTranslation(a.getTranslation());
                        return word;
                    }).collect(Collectors.toSet()));

            steps.add(step);
        }

        steps = stepsRepository.saveAll(steps);

        return steps;
    }
}
