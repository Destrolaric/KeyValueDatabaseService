package com.destrolaric.keyvaluestorage.components;

import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Special class to clean rows with ended ttl,
 * due to h2 not having functionality we do it manually
 */
@Component
@EnableAsync
public class CleanerDAO {

    @Autowired
    TimedKeyValueRepository timedKeyValueRepository;

    @PostConstruct
    @Async
    @Scheduled(fixedDelay = 1)
    void cleanOldRows() {
        timedKeyValueRepository.deleteAllExpiredRows();
    }
}
