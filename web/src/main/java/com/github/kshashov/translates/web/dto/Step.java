package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Step {
    private Long id;
    private String text;
    private List<Answer> answers;
    private List<Word> words;

    @Getter
    @Setter
    public static class Answer {
        private String text;
    }

    @Getter
    @Setter
    public static class Word {
        private String source;
        private String translation;
    }
}
