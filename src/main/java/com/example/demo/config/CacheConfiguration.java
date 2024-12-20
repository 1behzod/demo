package com.example.demo.config;

import com.example.demo.constants.Constants;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CacheConfiguration {


    /*@Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }*/

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.javax.cache.cache_manager", cacheManager);
        };
    }


    @Bean
    public JCacheManagerCustomizer jCacheManagerCustomizer() {
        return cacheManager -> {
            createCache(cacheManager, Constants.CATEGORY_LIST, 3600, 1000);
            createCache(cacheManager, Constants.PRODUCT_LIST, 3600, 100);
        };
    }

    private void createCache(javax.cache.CacheManager cacheManager, String cacheName, long ttlSeconds, long heapSize) {
        javax.cache.Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cacheManager.createCache(
                    cacheName,
                    Eh107Configuration.fromEhcacheCacheConfiguration(
                            CacheConfigurationBuilder
                                    .newCacheConfigurationBuilder(
                                            Object.class,
                                            Object.class,
                                            ResourcePoolsBuilder.heap(heapSize)
                                    )
                                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofSeconds(ttlSeconds)))
                                    .build()
                    )
            );
        }
    }
}
