package com.github.kshashov.translates.data.repos;

import com.github.kshashov.translates.data.entities.UserAnswer;
import com.github.kshashov.translates.data.entities.UserAnswerIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserAnswersRepository extends JpaRepository<UserAnswer, UserAnswerIdentity>, JpaSpecificationExecutor<UserAnswer>, BaseRepo {
    List<UserAnswer> findAllByStepExerciseIdAndIdentityUserIdOrderByIdentityCreatedAtAsc(@Param("exerciseId") Long exerciseId, @Param("userId") Long userId);

    @Modifying
    @Transactional(propagation = Propagation.REQUIRED)
    void deleteAllByStepExerciseId(@Param("exerciseId") Long exerciseId);
}
