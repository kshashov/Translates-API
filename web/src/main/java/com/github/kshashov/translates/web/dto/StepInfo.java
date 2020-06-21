package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StepInfo {
    private String text;
    private List<AnswerInfo> answers;
    private List<WordInfo> words;

    @Getter
    @Setter
    public static class AnswerInfo {
        private String text;
    }

    @Getter
    @Setter
    public static class WordInfo {
        private String source;
        private String translation;
    }
}


