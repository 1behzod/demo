package com.example.demo.repository;

import com.example.demo.domain.Category;
import com.example.demo.repository.custom.CategoryRepositoryCustom;

public interface CategoryRepository extends BaseRepository<Category, Long>, CategoryRepositoryCustom {

    boolean existsByName(String name);
}
