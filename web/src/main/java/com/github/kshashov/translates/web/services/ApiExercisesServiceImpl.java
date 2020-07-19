package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.common.errors.NotFoundException;
import com.github.kshashov.translates.data.entities.User;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.StepsStats;
import com.github.kshashov.translates.data.repos.UserAnswersStats;
import com.github.kshashov.translates.data.services.ExercisesService;
import com.github.kshashov.translates.web.dto.*;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import com.github.kshashov.translates.web.security.SecurityUtils;
import com.github.kshashov.translates.web.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ApiExercisesServiceImpl implements ApiExercisesService {
    private final ExercisesRepository repository;
    private final ExercisesService service;
    private final DtoMapper mapper;

    @Autowired
    public ApiExercisesServiceImpl(ExercisesRepository repository, ExercisesService service, DtoMapper mapper) {
        this.repository = repository;
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_EXERCISES.getCode())")
    public Paged<Exercise> getExercises(Pageable pageable, String filter, Long from, Long to, Long tag) {
        Specification<com.github.kshashov.translates.data.entities.Exercise> spec = (Specification<com.github.kshashov.translates.data.entities.Exercise>) (root, query, criteriaBuilder) -> {

            Predicate filterLike = criteriaBuilder.conjunction();

            if (!StringUtils.isBlank(filter)) {
                Predicate titleLike = criteriaBuilder
                        .like(
                                criteriaBuilder.upper(root.get("title")),
                                "%" + filter.toUpperCase() + "%");

                Join<Exercise, User> userJoin = root.join("creator");
                Predicate creatorLike = criteriaBuilder
                        .like(
                                criteriaBuilder.upper(userJoin.get("name")),
                                "%" + filter.toUpperCase() + "%");

                filterLike = criteriaBuilder.or(titleLike, creatorLike);
            }

            Predicate fromEquals = from == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("from"), from);

            Predicate toEquals = to == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("to"), to);

            Predicate tagEquals = tag == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.isMember(tag, root.get("tags"));

            return criteriaBuilder.and(filterLike, fromEquals, toEquals, tagEquals);
        };

        return Paged.of(repository.findAll(spec, pageable), mapper::toExercise);
    }

    @Override
    public Paged<StatsExercise> getExercisesWithStats(Pageable pageable, Long from, Long to, Long tag) {
        Specification<com.github.kshashov.translates.data.entities.Exercise> spec = (Specification<com.github.kshashov.translates.data.entities.Exercise>) (root, query, criteriaBuilder) -> {

            Predicate fromEquals = from == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("from"), from);

            Predicate toEquals = to == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("to"), to);

            Predicate tagEquals = tag == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.isMember(tag, root.get("tags"));

            return criteriaBuilder.and(fromEquals, toEquals, tagEquals);
        };

        Paged<StatsExercise> paged = Paged.of(repository.findAll(spec, pageable), mapper::toStatsExercise);

        // Get stats for user answers
        List<Long> ids = paged.getItems().stream()
                .map(Exercise::getId)
                .collect(Collectors.toList());

        Long userId = SecurityUtils.getCurrentUser()
                .map(UserPrincipal::getUser)
                .map(User::getId).orElse(null);

        // Aggregated stats by exercises
        Map<Long, MutablePair<Integer, Integer>> aggregatedStats;

        if (userId != null) {
            // Get stats with user answers data
            aggregatedStats = new HashMap<>();
            List<UserAnswersStats> stats = repository.getUserStats(userId, ids);

            for (UserAnswersStats eStats : stats) {
                Long id = eStats.getExerciseId();
                if (!aggregatedStats.containsKey(id)) {
                    aggregatedStats.put(id, new MutablePair<>(0, 0));
                }

                MutablePair<Integer, Integer> aggregated = aggregatedStats.get(id);
                if (eStats.getStepId() != null) {
                    aggregated.setLeft(aggregated.getLeft() + 1);
                    if ((eStats.getSuccess() != null) && eStats.getSuccess()) {
                        aggregated.setRight(aggregated.getRight() + 1);
                    }
                }
            }
        } else {
            // Get stats without user related data
            List<StepsStats> stats = repository.getStepsStats(ids);

            aggregatedStats = stats.stream()
                    .collect(Collectors.toMap(
                            StepsStats::getExerciseId,
                            s -> new MutablePair<>(s.getStepsTotal().intValue(), 0)));

        }

        // Add stats to result
        paged.getItems().forEach(e -> {
            MutablePair<Integer, Integer> aggregated = aggregatedStats.get(e.getId());
            e.setStepsTotal(aggregated.getLeft());
            e.setUserScore(aggregated.getRight());
        });

        return paged;
    }

    @Override
    public Exercise getExercise(Long id) {
        Objects.requireNonNull(id);

        if (!repository.existsById(id)) {
            throw new NotFoundException("Exercise is not found");
        }

        com.github.kshashov.translates.data.entities.Exercise exercise = repository.findById(id).get();
        return mapper.toExercise(exercise);
    }

    @Override
    @PreAuthorize("isAuthenticated() && (#info.creator == authentication.principal.user.id)")
    public Exercise createExercise(ExerciseInfo info) {
        Objects.requireNonNull(info);

        ExercisesService.ExerciseInfo i = mapper.toExerciseInfo(info);
        com.github.kshashov.translates.data.entities.Exercise exercise = service.createExercise(i);
        return mapper.toExercise(exercise);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_EXERCISES.getCode())")
    public void updateExercise(Long id, ExerciseInfo info) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(info);

        if (!repository.existsById(id)) {
            throw new NotFoundException("Exercise is not found");
        }

        ExercisesService.ExerciseInfo i = mapper.toExerciseInfo(info);
        service.updateExercise(id, i);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_EXERCISES.getCode())")
    public void delete(Long id) {
        service.delete(id);
    }

    @Override
    public ExercisesStats getStats() {
        Map<Long, Long> byFrom = repository.countByFrom().stream()
                .collect(Collectors.toMap(i -> i.getLangId(), i -> i.getCount()));

        Map<Long, Long> byTo = repository.countByTo().stream()
                .collect(Collectors.toMap(i -> i.getLangId(), i -> i.getCount()));

        Map<Long, Long> byTags = repository.countByTags().stream()
                .collect(Collectors.toMap(i -> i.getTagId(), i -> i.getCount()));

        return new ExercisesStats(byFrom, byTo, byTags);
    }
}
