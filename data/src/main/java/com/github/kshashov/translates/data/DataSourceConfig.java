package com.github.kshashov.translates.data;

import com.github.kshashov.translates.data.entities.BaseEntity;
import com.github.kshashov.translates.data.repos.BaseRepo;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = BaseRepo.class)
@EntityScan(basePackageClasses = {BaseEntity.class})
public class DataSourceConfig {
}
