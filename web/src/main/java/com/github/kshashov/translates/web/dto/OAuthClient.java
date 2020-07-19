package com.github.kshashov.translates.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OAuthClient {
    private final String title;
    private final String url;
}
