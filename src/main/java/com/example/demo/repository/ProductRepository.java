package com.example.demo.repository;

import com.example.demo.constants.Constants;
import com.example.demo.domain.Product;
import com.example.demo.repository.custom.ProductRepositoryCustom;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {

    boolean existsByName(String name);

    @Cacheable(cacheNames = Constants.PRODUCT_BY_ID)
    Optional<Product> findById(Long id);
}
