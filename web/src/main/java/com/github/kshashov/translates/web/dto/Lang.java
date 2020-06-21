package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lang {
    private Long id;
    private String title;
    private String code;

    public static Lang of(com.github.kshashov.translates.data.entities.Language language) {
        if (language == null) return null;

        Lang l = new Lang();
        l.setId(language.getId());
        l.setCode(language.getCode());
        l.setTitle(language.getTitle());
        return l;
    }
}
