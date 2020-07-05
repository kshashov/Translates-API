package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.UserAnswer;
import com.github.kshashov.translates.web.dto.UserAnswerInfo;
import com.github.kshashov.translates.web.services.ApiUserAnswersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userAnswers")
public class UserAnswersController {
    private final ApiUserAnswersService userAnswersService;

    @Autowired
    public UserAnswersController(ApiUserAnswersService userAnswersService) {
        this.userAnswersService = userAnswersService;
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearer")
    public Map<Long, List<UserAnswer>> getUserAnswers(@RequestParam("exerciseId") Long exerciseId, @RequestParam("userId") Long userId) {
        return userAnswersService.getUserAnswersForExercise(exerciseId, userId);
    }

    @PostMapping("")
    @SecurityRequirement(name = "bearer")
    public UserAnswer createUserAnswer(@RequestBody UserAnswerInfo answerInfo) {
        return userAnswersService.createUserAnswer(answerInfo);
    }
}
