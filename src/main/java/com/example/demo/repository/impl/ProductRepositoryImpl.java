package com.example.demo.repository.impl;

import com.example.demo.domain.Product;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import com.example.demo.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.Collections;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ResultList<Product> getResultList(BaseFilter filter) {
        ResultList<Product> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.* FROM product p ");
        sql.append("WHERE 1=1");

        if (filter.isSearchNotEmpty()) {
            sql.append(" AND LOWER(p.name) LIKE :searchKey ");
        }
        if (filter.getCategoryId() != null) {
            sql.append(" AND p.category_id = :categoryId ");
        }
        sql.append(" ORDER BY p.")
                .append(filter.getOrderBy())
                .append(" ")
                .append(filter.getSortOrder());

        String paginatedSql = sql + " LIMIT :limit OFFSET :offset";

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS countQuery";
        Long totalCount = ((Number) entityManager.createNativeQuery(countSql).getSingleResult()).longValue();

        if (totalCount == 0) {
            resultList.setList(Collections.emptyList());
            resultList.setCount(0L);
            return resultList;
        }

        Query query = entityManager.createNativeQuery(paginatedSql, Product.class);
        if (filter.isSearchNotEmpty()) {
            query.setParameter("searchKey", "%" + filter.getSearchFor().toLowerCase() + "%");
        }
        if (filter.getCategoryId() != null) {
            query.setParameter("categoryId", filter.getCategoryId());
        }
        query.setParameter("limit", filter.getSize());
        query.setParameter("offset", filter.getStart());

        resultList.setList(query.getResultList());
        resultList.setCount(totalCount);

        return resultList;
    }

}
