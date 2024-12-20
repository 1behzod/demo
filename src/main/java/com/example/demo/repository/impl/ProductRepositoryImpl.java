package com.example.demo.repository.impl;

import com.example.demo.domain.Product;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import com.example.demo.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.persistence.TypedQuery;

import java.util.Collections;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ResultList<Product> getResultList(BaseFilter filter) {
        ResultList<Product> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select p from Product p ");
        sql.append("where 1=1");

        if (filter.isSearchNotEmpty()) {
            sql.append(" and lower(p.name) ").append(filter.getLikeSearch());
        }
        if (filter.getCategoryId() != null) {
            sql.append(" and p.category.id = :categoryId");
        }

        String countSql = sql.toString().replaceFirst("select p", "select count(p.id)");

        sql.append(" order by p.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Product> query = entityManager
                .createQuery(sql.toString(), Product.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.isSearchNotEmpty()) {
            query.setParameter("searchKey", filter.getSearchFor());
            countQuery.setParameter("searchKey", filter.getSearchFor());
        }
        if (filter.getCategoryId() != null) {
            query.setParameter("categoryId", filter.getCategoryId());
            countQuery.setParameter("categoryId", filter.getCategoryId());
        }

        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }

        return resultList;
    }


}
