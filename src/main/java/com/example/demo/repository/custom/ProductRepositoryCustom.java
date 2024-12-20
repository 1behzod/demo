package com.example.demo.repository.custom;

import com.example.demo.domain.Product;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryCustom {


    ResultList<Product> getResultList(BaseFilter filter);

}
