package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class Exercise {
    private Long id;
    private String title;
    private PublicUser creator;
    private Lang from;
    private Lang to;
    private List<Tag> tags;

    protected static Exercise of(Exercise instance, com.github.kshashov.translates.data.entities.Exercise exercise) {
        Objects.requireNonNull(instance);
        if (exercise == null) return null;

        instance.setId(exercise.getId());
        instance.setTitle(exercise.getTitle());
        instance.setCreator(PublicUser.of(exercise.getCreator()));
        instance.setFrom(Lang.of(exercise.getFrom()));
        instance.setTo(Lang.of(exercise.getTo()));
        instance.setTags(exercise.getTags()
                .stream()
                .map(Tag::of)
                .collect(Collectors.toList()));
        return instance;
    }

    public static Exercise of(com.github.kshashov.translates.data.entities.Exercise exercise) {
        return of(new Exercise(), exercise);
    }
}
