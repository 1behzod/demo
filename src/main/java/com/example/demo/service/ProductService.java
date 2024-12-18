package com.example.demo.service;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDTO;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import com.example.demo.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class ProductService extends BaseService {

    ProductRepository productRepository;
     CacheService cacheService;

    private void validate(ProductDTO productDTO) {
        if (!StringUtils.hasLength(productDTO.getName())) {
            throw badRequestExceptionThrow("Product name is required").get();
        }
        if (!StringUtils.hasLength(String.valueOf(productDTO.getCategoryId()))) {
            throw badRequestExceptionThrow("Category id is required").get();
        }
        if (productRepository.existsByName(productDTO.getName())) {
            throw badRequestExceptionThrow("Product name already exists").get();
        }
    }

    @Transactional
    public Long create(ProductDTO productDTO) {
        validate(productDTO);
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategoryId(productDTO.getCategoryId());
        productRepository.save(product);
        return product.getId();
    }

    public Page<ProductDTO> getList(BaseFilter filter) {
        cacheService.getCaches();
        productRepository.findById(1L).ifPresent(product -> {
            log.info("Product: {}",product.getName());
        });
//        ResultList<Product> resultList = productRepository.getResultList(filter);
//        List<ProductDTO> result = resultList
//                .getList()
//                .stream()
//                .map(product -> {
//                    ProductDTO productDTO = new ProductDTO();
//                    productDTO.setId(product.getId());
//                    productDTO.setName(product.getName());
//                    productDTO.setCategoryId(product.getCategoryId());
//                    productDTO.setPrice(product.getPrice());
//                    return productDTO;
//                })
//                .collect(Collectors.toList());
        return new PageImpl<>(new ArrayList<>(), filter.getOrderedPageable(), 0);

    }

}
