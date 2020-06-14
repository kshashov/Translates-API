package com.github.kshashov.translates.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paged<T> {
    private int page;
    private int size;
    private int totalPages;
    private long totalItems;
    private List<T> items;

    public static <T, R> Paged<T> of(Page<R> page, Function<? super R, ? extends T> elementMapper) {
        Paged<T> paged = new Paged<>();
        paged.setPage(page.getNumber());
        paged.setSize(page.getSize());
        paged.setTotalItems(page.getTotalElements());
        paged.setTotalPages(page.getTotalPages());
        paged.setItems(page.getContent()
                .stream()
                .map(elementMapper)
                .collect(Collectors.toList()));

        return paged;
    }
}
