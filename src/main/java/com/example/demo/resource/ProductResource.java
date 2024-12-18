package com.example.demo.resource;

import com.example.demo.dto.ProductDTO;
import com.example.demo.filter.BaseFilter;
import com.example.demo.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductResource {

    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.create(productDTO));
    }

    @GetMapping("/getList")
    public ResponseEntity<Page<ProductDTO>> getList(@ParameterObject BaseFilter filter) {
        return ResponseEntity.ok(productService.getList(filter));

    }


}
