package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.Tag;
import com.github.kshashov.translates.web.services.ApiTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
    private final ApiTagsService tagsService;

    @Autowired
    public TagsController(ApiTagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagsService.getTags();
    }
}
