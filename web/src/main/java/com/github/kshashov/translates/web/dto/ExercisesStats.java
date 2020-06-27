package com.github.kshashov.translates.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExercisesStats {
    Map<Long, Long> byFrom;
    Map<Long, Long> byTo;
    Map<Long, Long> byTags;
}
