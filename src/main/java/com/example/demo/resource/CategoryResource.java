package com.example.demo.resource;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.filter.BaseFilter;
import com.example.demo.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryResource {

    CategoryService categoryService;


    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.create(categoryDTO));
    }

    @GetMapping("/getList")
    public ResponseEntity<Page<CategoryDTO>> getList(@ParameterObject BaseFilter filter) {
        return ResponseEntity.ok(categoryService.getList(filter));

    }
}
