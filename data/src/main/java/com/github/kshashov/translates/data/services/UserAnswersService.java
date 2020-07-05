package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.UserAnswer;
import lombok.Getter;
import lombok.Setter;

public interface UserAnswersService {

    UserAnswer createUserAnswer(UserAnswerInfo info);

    @Getter
    @Setter
    class UserAnswerInfo {
        private Long userId;
        private Long stepId;
        private String text;
        private Boolean success;
    }
}
