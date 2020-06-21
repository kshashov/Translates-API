package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.data.entities.Exercise;
import com.github.kshashov.translates.data.repos.ExercisesRepository;
import com.github.kshashov.translates.data.repos.LanguagesRepository;
import com.github.kshashov.translates.data.repos.TagsRepository;
import com.github.kshashov.translates.data.repos.UsersRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExercisesServiceImpl implements ExercisesService {
    private final ExercisesRepository repository;
    private final LanguagesRepository languagesRepository;
    private final TagsRepository tagsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public ExercisesServiceImpl(
            ExercisesRepository repository,
            LanguagesRepository languagesRepository,
            TagsRepository tagsRepository,
            UsersRepository usersRepository) {
        this.repository = repository;
        this.languagesRepository = languagesRepository;
        this.tagsRepository = tagsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Exercise createExercise(ExerciseInfo exerciseInfo) {
        Objects.requireNonNull(exerciseInfo);

        try {
            return doCreateExercise(exerciseInfo);
        } catch (javax.validation.ConstraintViolationException ex) {
            throw new BadRequestException("Invalid request", ex);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException casted = (ConstraintViolationException) ex.getCause();
                if ("exercises_unique_title".equals(casted.getConstraintName())) {
                    throw new BadRequestException("Exercise " + exerciseInfo.getTitle() + " already exists", ex);
                } else {
                    throw new BadRequestException("Invalid request", ex);
                }
            }
            throw ex;
        }
    }

    private Exercise doCreateExercise(ExerciseInfo info) {
        Objects.requireNonNull(info.getCreator());
        Objects.requireNonNull(info.getFrom());
        Objects.requireNonNull(info.getTo());
        Objects.requireNonNull(info.getTags());

        Exercise exercise = new Exercise();
        exercise.setTitle(info.getTitle());
        exercise.setCreator(usersRepository.getOne(info.getCreator()));
        exercise.setFrom(languagesRepository.getOne(info.getFrom()));
        exercise.setTo(languagesRepository.getOne(info.getTo()));
        exercise.setTags(info.getTags().stream()
                .map(tagsRepository::getOne)
                .collect(Collectors.toSet()));
        return repository.save(exercise);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateExercise(Long id, ExerciseInfo exerciseInfo) {
        Objects.requireNonNull(exerciseInfo);

        try {
            doUpdateExercise(id, exerciseInfo);
        } catch (javax.validation.ConstraintViolationException ex) {
            throw new BadRequestException("Invalid request", ex);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException casted = (ConstraintViolationException) ex.getCause();
                if ("exercises_unique_title".equals(casted.getConstraintName())) {
                    throw new BadRequestException("Exercise " + exerciseInfo.getTitle() + " already exists", ex);
                } else {
                    throw new BadRequestException("Invalid request", ex);
                }
            }
            throw ex;
        }
    }

    private void doUpdateExercise(Long id, ExerciseInfo info) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(info.getCreator());
        Objects.requireNonNull(info.getFrom());
        Objects.requireNonNull(info.getTo());
        Objects.requireNonNull(info.getTags());

        Exercise exercise = repository.findById(id).get();

        if (!exercise.getCreator().getId().equals(info.getCreator())) {
            throw new BadRequestException("Exercise creator can't be changed");
        }

        exercise.setTitle(info.getTitle());
        exercise.setFrom(languagesRepository.getOne(info.getFrom()));
        exercise.setTo(languagesRepository.getOne(info.getTo()));
        exercise.setTags(info.getTags().stream()
                .map(tagsRepository::getOne)
                .collect(Collectors.toSet()));
        repository.save(exercise);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
