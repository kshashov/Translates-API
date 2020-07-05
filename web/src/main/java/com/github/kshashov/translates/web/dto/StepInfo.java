package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class StepInfo {
    @NotBlank(message = "Text is empty")
    private String text;
    @NotEmpty(message = "Answer is required")
    private List<AnswerInfo> answers;
    private List<WordInfo> words;

    @Getter
    @Setter
    public static class AnswerInfo {
        @NotBlank(message = "Answer text is empty")
        private String text;
    }

    @Getter
    @Setter
    public static class WordInfo {
        @NotBlank(message = "Word is empty")
        private String source;
        @NotBlank(message = "Word translation is empty")
        private String translation;
    }
}


