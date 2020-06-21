package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.TagsRepository;
import com.github.kshashov.translates.web.dto.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiTagsServiceImpl implements ApiTagsService {
    private final TagsRepository tagsRepository;

    @Autowired
    public ApiTagsServiceImpl(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    @Override
    public List<Tag> getTags() {
        return tagsRepository.findAll(Sort.by("title")).stream()
                .map(Tag::of)
                .collect(Collectors.toList());
    }
}
