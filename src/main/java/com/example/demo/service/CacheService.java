package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.cache.Cache;
import javax.cache.CacheManager;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    CacheManager cacheManager;

    public void getCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache.iterator().hasNext()) {
                cache.iterator().forEachRemaining(System.out::println);
            }
            log.info("Cache name: {}", cacheName);
        }
    }
}
