package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.web.dto.UserAnswer;
import com.github.kshashov.translates.web.dto.UserAnswerInfo;

import java.util.List;
import java.util.Map;

public interface ApiUserAnswersService {
    Map<Long, List<UserAnswer>> getUserAnswersForExercise(Long exerciseId, Long userId);

    UserAnswer createUserAnswer(UserAnswerInfo info);
}
