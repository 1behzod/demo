package com.example.demo.repository.custom;

import com.example.demo.domain.Category;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryCustom {
    ResultList<Category> getResultList(BaseFilter filter);
}
