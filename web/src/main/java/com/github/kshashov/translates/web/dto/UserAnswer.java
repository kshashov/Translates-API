package com.github.kshashov.translates.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAnswer {

    private Long userId;
    private Long stepId;
    private String text;
    private Boolean success;

    public static UserAnswer of(com.github.kshashov.translates.data.entities.UserAnswer answer) {
        UserAnswer a = new UserAnswer();
        a.setUserId(answer.getIdentity().getUserId());
        a.setStepId(answer.getIdentity().getStepId());
        a.setText(answer.getText());
        a.setSuccess(answer.getSuccess());
        return a;
    }
}
