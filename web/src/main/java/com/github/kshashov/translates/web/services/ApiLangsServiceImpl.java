package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.data.repos.LanguagesRepository;
import com.github.kshashov.translates.web.dto.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiLangsServiceImpl implements ApiLangsService {
    private final LanguagesRepository repository;

    @Autowired
    public ApiLangsServiceImpl(LanguagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Lang> getLangs() {
        return repository.findAll(Sort.by("code")).stream()
                .map(Lang::of)
                .collect(Collectors.toList());
    }
}
