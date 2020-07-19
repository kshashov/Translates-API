package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Exercise {
    private Long id;
    private String title;
    private PublicUser creator;
    private Lang from;
    private Lang to;
    private List<Tag> tags;
}
