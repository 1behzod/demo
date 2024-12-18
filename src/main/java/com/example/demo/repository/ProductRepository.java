package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.repository.custom.ProductRepositoryCustom;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {

    boolean existsByName(String name);
}
