package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    private Long id;
    private String title;

    public static Tag of(com.github.kshashov.translates.data.entities.Tag tag) {
        if (tag == null) return null;

        Tag t = new Tag();
        t.setId(tag.getId());
        t.setTitle(tag.getTitle());
        return t;
    }
}
