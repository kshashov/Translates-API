package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.LanguagesRepository;
import com.github.kshashov.translates.web.dto.Lang;
import com.github.kshashov.translates.web.dto.mappings.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiLangsServiceImpl implements ApiLangsService {
    private final LanguagesRepository repository;
    private final DtoMapper mapper;

    @Autowired
    public ApiLangsServiceImpl(LanguagesRepository repository, DtoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Lang> getLangs() {
        return mapper.toLang(repository.findAll(Sort.by("code")));
    }
}
