package com.github.kshashov.translates.data;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext
@ContextConfiguration(classes = MainTestConfiguration.class)
public class BaseTest {
}
