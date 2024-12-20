package com.example.demo.repository.custom;

import com.example.demo.constants.Constants;
import com.example.demo.domain.Product;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {

    @Cacheable(cacheNames = Constants.PRODUCT_LIST)
    ResultList<Product> getResultList(BaseFilter filter);

}
