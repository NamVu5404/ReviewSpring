package com.NamVu.ReviewSpring.repository;

import com.NamVu.ReviewSpring.dto.response.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> search(String search, int pageNo, int pageSize, String sortBy, String direction) {

        StringBuilder sqlQuery = new StringBuilder("select new com.NamVu.ReviewSpring.dto.response.UserDetailResponse(u.id, u.firstName, u.lastName, u.email, u.phone) from User u where 1=1 ");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and lower(u.username) like lower(:username) ");
            sqlQuery.append(" or lower(u.firstName) like lower(:firstName) ");
            sqlQuery.append(" or lower(u.lastName) like lower(:lastName) ");
        }
        sqlQuery.append(String.format(" order by u.%s %s ", sortBy, direction));

        Query selectQuery = entityManager.createQuery(sqlQuery.toString());
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        if (StringUtils.hasLength(search)) {
            selectQuery.setParameter("username", String.format("%%%s%%", search));
            selectQuery.setParameter("firstName", String.format("%%%s%%", search));
            selectQuery.setParameter("lastName", String.format("%%%s%%", search));
        }

        List users = selectQuery.getResultList();
        long totalElements = countTotalElements(search);

        Page<?> page = new PageImpl<Object>(users, PageRequest.of(pageNo, pageSize), totalElements);

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .data((List<Object>) page.stream().toList())
                .build();
    }

    private long countTotalElements(String search) {
        StringBuilder sqlQuery = new StringBuilder("select count(*) from User u where 1=1 ");
        if (StringUtils.hasLength(search)) {
            sqlQuery.append(" and lower(u.username) like lower(?1) ");
            sqlQuery.append(" or lower(u.firstName) like lower(?2) ");
            sqlQuery.append(" or lower(u.lastName) like lower(?3) ");
        }

        Query countQuery = entityManager.createQuery(sqlQuery.toString());
        if (StringUtils.hasLength(search)) {
            countQuery.setParameter(1, String.format("%%%s%%", search));
            countQuery.setParameter(2, String.format("%%%s%%", search));
            countQuery.setParameter(3, String.format("%%%s%%", search));
        }
        return (long) countQuery.getSingleResult();
    }
}
