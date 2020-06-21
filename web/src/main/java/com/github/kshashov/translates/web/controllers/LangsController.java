package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.Lang;
import com.github.kshashov.translates.web.services.ApiLangsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/langs")
public class LangsController {
    private final ApiLangsService langsService;

    @Autowired
    public LangsController(ApiLangsService langsService) {
        this.langsService = langsService;
    }

    @GetMapping
    public List<Lang> getLangs() {
        return langsService.getLangs();
    }
}
