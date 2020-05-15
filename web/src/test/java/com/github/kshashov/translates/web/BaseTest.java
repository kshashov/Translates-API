package com.github.kshashov.translates.web;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(classes = {TranslatesApi.class})
public class BaseTest {
}
