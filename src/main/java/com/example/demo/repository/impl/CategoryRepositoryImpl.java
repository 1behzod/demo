package com.example.demo.repository.impl;

import com.example.demo.domain.Category;
import com.example.demo.filter.BaseFilter;
import com.example.demo.filter.ResultList;
import com.example.demo.repository.custom.CategoryRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    //   @Cacheable(cacheNames = Constants.CATEGORY_LIST)
    public ResultList<Category> getResultList(BaseFilter filter) {

        ResultList<Category> resultList = new ResultList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select c from Category c ");
        sql.append(" where 1=1");

        if (filter.isSearchNotEmpty()) {
            sql.append(" and lower(c.name) ").append(filter.getLikeSearch());
        }

        String countSql = sql.toString().replaceFirst("select c", "select count(c.id)");

        sql.append(" order by c.").append(filter.getOrderBy());
        sql.append(" ").append(filter.getSortOrder());

        TypedQuery<Category> query = entityManager
                .createQuery(sql.toString(), Category.class)
                .setFirstResult(filter.getStart())
                .setMaxResults(filter.getSize());

        TypedQuery<Long> countQuery = entityManager.createQuery(countSql, Long.class);

        if (filter.isSearchNotEmpty()) {
            query.setParameter("searchKey", filter.getSearchFor());
            countQuery.setParameter("searchKey", filter.getSearchFor());
        }

        Long count = countQuery.getSingleResult();
        if (count > 0) {
            resultList.setList(query.getResultList());
            resultList.setCount(count);
        }
        return resultList;
    }

}
