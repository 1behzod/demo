package com.example.demo.repository.custom;

import com.example.demo.constants.Constants;
import com.example.demo.domain.Category;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryCustom {

    @Cacheable(cacheNames = Constants.CATEGORY_LIST)
    ResultList<Category> getResultList(BaseFilter filter);
}
