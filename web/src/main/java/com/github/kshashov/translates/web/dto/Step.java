package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Step {
    private Long id;
    private String text;
    private List<Answer> answers;
    private List<Word> words;

    public static Step of(com.github.kshashov.translates.data.entities.Step step) {
        if (step == null) return null;

        Step s = new Step();
        s.setId(step.getId());
        s.setText(step.getText());
        s.setAnswers(step.getAnswers().stream().map(Answer::of).collect(Collectors.toList()));
        s.setWords(step.getWords().stream().map(Word::of).collect(Collectors.toList()));
        return s;
    }

    @Getter
    @Setter
    public static class Answer {
        private String text;

        public static Answer of(com.github.kshashov.translates.data.entities.Answer answer) {
            if (answer == null) return null;

            Answer a = new Answer();
            a.setText(answer.getText());
            return a;
        }
    }

    @Getter
    @Setter
    public static class Word {
        private String source;
        private String translation;

        public static Word of(com.github.kshashov.translates.data.entities.Word word) {
            if (word == null) return null;

            Word a = new Word();
            a.setSource(word.getSource());
            a.setTranslation(word.getTranslation());
            return a;
        }
    }
}
