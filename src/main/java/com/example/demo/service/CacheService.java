package com.example.demo.service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    CacheManager cacheManager;

/*    public void getCaches() {
        for (String cacheName : cacheManager.getCacheNames()) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache.iterator().hasNext()) {
                cache.iterator().forEachRemaining(System.out::println);
            }
            log.info("Cache name: {}", cacheName);
        }
    }*/

    public List<String> getList() {
        return new ArrayList<>((Collection<String>) cacheManager.getCacheNames());
    }

    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    public void clearAll() {
        cacheManager
                .getCacheNames()
                .forEach(cacheName -> {
                    try {
                        this.clear(cacheName);
                    } catch (Exception e) {
                        log.error("Clear cache {} error {} :", cacheName, e.getMessage());
                    }
                });

    }

    public void evict(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
            //cache.remove(key); javax cache
        }
    }


    public void evictCategory(Category category) {
        if (category != null) {
            this.evict(category.getName(), category.getId());
        }
    }

    public void evictProduct(Product product) {
        if (product != null) {
            this.evict(product.getName(), product.getId());
        }
    }

}
