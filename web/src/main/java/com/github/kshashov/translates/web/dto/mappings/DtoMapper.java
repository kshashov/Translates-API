package com.github.kshashov.translates.web.dto.mappings;

import com.github.kshashov.translates.data.services.ExercisesService;
import com.github.kshashov.translates.data.services.StepsService;
import com.github.kshashov.translates.data.services.UserAnswersService;
import com.github.kshashov.translates.data.services.UsersService;
import com.github.kshashov.translates.web.dto.*;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DtoMapper {
    DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

    // Exercises

    Exercise toExercise(com.github.kshashov.translates.data.entities.Exercise exercise);

    @IterableMapping(elementTargetType = Exercise.class)
    List<Exercise> toExercise(Collection<com.github.kshashov.translates.data.entities.Exercise> exercises);

    ExercisesService.ExerciseInfo toExerciseInfo(ExerciseInfo exerciseInfo);

    List<ExercisesService.ExerciseInfo> toExerciseInfo(Collection<ExerciseInfo> exerciseInfo);

    @Mapping(target = "userScore", ignore = true)
    @Mapping(target = "stepsTotal", ignore = true)
    StatsExercise toStatsExercise(com.github.kshashov.translates.data.entities.Exercise exercise);

    @IterableMapping(elementTargetType = StatsExercise.class)
    List<StatsExercise> toStatsExercise(Collection<com.github.kshashov.translates.data.entities.Exercise> exercises);

    Step toStep(com.github.kshashov.translates.data.entities.Step step);

    List<Step> toStep(Collection<com.github.kshashov.translates.data.entities.Step> step);

    StepsService.StepInfo toStepInfo(StepInfo stepInfo);

    List<StepsService.StepInfo> toStepInfo(Collection<StepInfo> stepInfo);

    Step.Word toWord(com.github.kshashov.translates.data.entities.Word word);

    List<Step.Word> toWord(Collection<com.github.kshashov.translates.data.entities.Word> word);

    StepsService.WordInfo toWordInfo(StepInfo.WordInfo stepInfo);

    List<StepsService.WordInfo> toWordInfo(Collection<StepInfo.WordInfo> stepInfo);

    Step.Answer toAnswer(com.github.kshashov.translates.data.entities.Answer answer);

    List<Step.Answer> toAnswer(Collection<com.github.kshashov.translates.data.entities.Answer> answer);

    StepsService.AnswerInfo toAnswerInfo(StepInfo.AnswerInfo stepInfo);

    List<StepsService.AnswerInfo> toAnswerInfo(Collection<StepInfo.AnswerInfo> stepInfo);

    Tag toTag(com.github.kshashov.translates.data.entities.Tag tag);

    List<Tag> toTag(Collection<com.github.kshashov.translates.data.entities.Tag> tag);

    Lang toLang(com.github.kshashov.translates.data.entities.Language lang);

    List<Lang> toLang(Collection<com.github.kshashov.translates.data.entities.Language> lang);

    // Users

    PublicUser toPublicUser(com.github.kshashov.translates.data.entities.User user);

    List<PublicUser> toPublicUser(Collection<com.github.kshashov.translates.data.entities.User> user);

    User toUser(com.github.kshashov.translates.data.entities.User user);

    List<User> toUser(Collection<com.github.kshashov.translates.data.entities.User> user);

    Role toRole(com.github.kshashov.translates.data.entities.Role role);

    List<Role> toRole(Collection<com.github.kshashov.translates.data.entities.Role> role);

    @Mappings({
            @Mapping(source = "identity.userId", target = "userId"),
            @Mapping(source = "identity.stepId", target = "stepId")
    })
    UserAnswer toUserAnswer(com.github.kshashov.translates.data.entities.UserAnswer userAnswer);

    @IterableMapping(elementTargetType = UserAnswer.class)
    List<UserAnswer> toUserAnswer(Collection<com.github.kshashov.translates.data.entities.UserAnswer> userAnswer);

    @Mapping(target = "success", ignore = true)
    UserAnswersService.UserAnswerInfo toUserAnswerInfo(UserAnswerInfo userAnswerInfo);

    List<UserAnswersService.UserAnswerInfo> toUserAnswerInfo(Collection<UserAnswerInfo> userAnswerInfo);

    UsersService.UserInfo toUserInfo(UserInfo userInfo);

    List<UsersService.UserInfo> toUserInfo(Collection<UserInfo> userInfo);
}
