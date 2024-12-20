package com.example.demo.repository;

import com.example.demo.domain.Category;
import com.example.demo.repository.custom.CategoryRepositoryCustom;
import org.springframework.cache.annotation.Cacheable;

public interface CategoryRepository extends BaseRepository<Category, Long>, CategoryRepositoryCustom {

    @Cacheable(cacheNames = "CATEGORY_BY_NAME")
    boolean existsByName(String name);

}
