package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Exercise {
    private Long id;
    private String title;
    private User creator;
    private Lang from;
    private Lang to;
    private List<Tag> tags;

    public static Exercise of(com.github.kshashov.translates.data.entities.Exercise exercise) {
        if (exercise == null) return null;

        Exercise e = new Exercise();
        e.setId(exercise.getId());
        e.setTitle(exercise.getTitle());
        e.setCreator(User.of(exercise.getCreator()));
        e.setFrom(Lang.of(exercise.getFrom()));
        e.setTo(Lang.of(exercise.getTo()));
        e.setTags(exercise.getTags()
                .stream()
                .map(Tag::of)
                .collect(Collectors.toList()));
        return e;
    }
}
