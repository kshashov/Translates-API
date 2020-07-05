package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.data.entities.UserAnswer;
import com.github.kshashov.translates.data.entities.UserAnswerIdentity;
import com.github.kshashov.translates.data.repos.UserAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
public class UserAnswersServiceImpl implements UserAnswersService {

    private final UserAnswersRepository repository;

    @Autowired
    public UserAnswersServiceImpl(UserAnswersRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UserAnswer createUserAnswer(UserAnswerInfo info) {
        try {
            return doCreateUserAnswer(info);
        } catch (javax.validation.ConstraintViolationException ex) {
            throw new BadRequestException("Invalid request", ex);
        }
    }

    private UserAnswer doCreateUserAnswer(UserAnswerInfo info) {
        Objects.requireNonNull(info.getUserId());
        Objects.requireNonNull(info.getStepId());

        UserAnswerIdentity identity = new UserAnswerIdentity();
        identity.setUserId(info.getUserId());
        identity.setStepId(info.getStepId());
        identity.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setIdentity(identity);
        userAnswer.setText(info.getText());
        userAnswer.setSuccess(info.getSuccess());

        return repository.save(userAnswer);
    }
}
