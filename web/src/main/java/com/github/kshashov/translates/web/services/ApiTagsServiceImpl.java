package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.TagsRepository;
import com.github.kshashov.translates.web.dto.Tag;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiTagsServiceImpl implements ApiTagsService {
    private final TagsRepository tagsRepository;
    private final DtoMapper mapper;

    @Autowired
    public ApiTagsServiceImpl(TagsRepository tagsRepository, DtoMapper mapper) {
        this.tagsRepository = tagsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Tag> getTags() {
        return mapper.toTag(tagsRepository.findAll(Sort.by("title")));
    }
}
