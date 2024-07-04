package org.rest.hactesttask.config;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;

@Configuration
public class HibernateSearchIndexer implements ApplicationListener<ApplicationReadyEvent> {
    private final EntityManager entityManager;

    public HibernateSearchIndexer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        SearchSession    searchSession = Search.session(entityManager);
        MassIndexer indexer = searchSession.massIndexer()
                .batchSizeToLoadObjects(30)
                .idFetchSize(200)
                .threadsToLoadObjects(10);
        try {
            indexer.startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}