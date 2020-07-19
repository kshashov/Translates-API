package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.entities.Answer;
import com.github.kshashov.translates.data.repos.AnswersRepository;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.StepsRepository;
import com.github.kshashov.translates.data.repos.UserAnswersRepository;
import com.github.kshashov.translates.data.services.UserAnswersService;
import com.github.kshashov.translates.web.dto.UserAnswer;
import com.github.kshashov.translates.web.dto.UserAnswerInfo;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ApiUserAnswersServiceImpl implements ApiUserAnswersService {
    private final UserAnswersService userAnswersService;
    private final UserAnswersRepository userAnswersRepository;
    private final AnswersRepository answersRepository;
    private final StepsRepository stepsRepository;
    private final ExercisesRepository exercisesRepository;
    private final DtoMapper mapper;

    @Autowired
    public ApiUserAnswersServiceImpl(UserAnswersService userAnswersService, UserAnswersRepository userAnswersRepository, AnswersRepository answersRepository, StepsRepository stepsRepository, ExercisesRepository exercisesRepository, DtoMapper mapper) {
        this.userAnswersService = userAnswersService;
        this.userAnswersRepository = userAnswersRepository;
        this.answersRepository = answersRepository;
        this.stepsRepository = stepsRepository;
        this.exercisesRepository = exercisesRepository;
        this.mapper = mapper;
    }

    @Override
    @PreAuthorize("isAuthenticated() && (#userId == authentication.principal.user.id)")
    public Map<Long, List<UserAnswer>> getUserAnswersForExercise(Long exerciseId, Long userId) {
        return mapper.toUserAnswer(userAnswersRepository.findAllByStepExerciseIdAndIdentityUserIdOrderByIdentityCreatedAtAsc(exerciseId, userId))
                .stream()
                .collect(Collectors.groupingBy(UserAnswer::getStepId));
    }

    @Override
    @PreAuthorize("isAuthenticated() && (#info.userId == authentication.principal.user.id)")
    public UserAnswer createUserAnswer(UserAnswerInfo info) {
        Objects.requireNonNull(info);

//        info.setText(StringUtils.normalizeSpace(info.getText()));
        UserAnswersService.UserAnswerInfo i = mapper.toUserAnswerInfo(info);

        // Check if answer is right
        List<Answer> answers = answersRepository.findAllByStepId(i.getStepId());
        boolean success = answers.stream().anyMatch(a -> a.getText().equals(i.getText()));
        i.setSuccess(success);

        return mapper.toUserAnswer(userAnswersService.createUserAnswer(i));
    }
}
