package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.domain.Category;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import com.example.demo.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class CategoryService extends BaseService {

    CategoryRepository categoryRepository;

    private void validate(CategoryDTO categoryDTO) {
        if (!StringUtils.hasLength(categoryDTO.getName())) {
            throw badRequestExceptionThrow("Category name is required").get();
        }
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw badRequestExceptionThrow("Category name already exists").get();
        }
    }

    @Transactional
    public Long create(CategoryDTO categoryDTO) {
        validate(categoryDTO);
        Category category = new Category();
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    @CacheEvict(cacheNames = Constants.CATEGORY_LIST, allEntries = true)
    public Long update(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(badRequestExceptionThrow("Category not found"));
        categoryDTO.setId(id);
        this.validate(categoryDTO);

        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        return category.getId();

    }

    @Transactional
    @CacheEvict(cacheNames = Constants.CATEGORY_LIST, allEntries = true)

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw badRequestExceptionThrow("Category not found").get();
        }
        categoryRepository.deleteById(id);
    }


    public Page<CategoryDTO> getList(BaseFilter filter) {
        ResultList<Category> resultList = categoryRepository.getResultList(filter);

        List<CategoryDTO> dtoList = resultList
                .getList()
                .stream()
                .map(category ->
                        new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, filter.getOrderedPageable(), resultList.getCount());
    }






    /*  public Page<Category> getList(BaseFilter filter) {
          ResultList<Category> resultList = categoryRepository.getResultList(filter);
          List<CategoryDTO> result = resultList
                  .getList()
                  .stream()
                  .map(category -> {
                      CategoryDTO categoryDTO = new CategoryDTO();
                      categoryDTO.setId(category.getId());
                      categoryDTO.setName(category.getName());
                      return categoryDTO;
                  })
                  .collect(Collectors.toList());
          return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
      }*/


}
