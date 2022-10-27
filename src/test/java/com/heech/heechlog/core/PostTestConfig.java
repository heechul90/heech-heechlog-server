package com.heech.heechlog.core;

import com.heech.heechlog.core.repository.PostQueryRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class PostTestConfig {

    @PersistenceContext EntityManager em;

    @Bean
    public PostQueryRepository postQueryRepository() {
        return new PostQueryRepository(em);
    }
}
