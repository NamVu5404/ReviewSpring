package com.NamVu.ReviewSpring.repository;

import com.NamVu.ReviewSpring.dto.response.PageResponse;
import com.NamVu.ReviewSpring.model.Address;
import com.NamVu.ReviewSpring.model.User;
import com.NamVu.ReviewSpring.repository.criteria.SearchCriteria;
import com.NamVu.ReviewSpring.repository.criteria.UserSearchQueryCriteriaConsumer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public PageResponse<User> searchUserByCriteria(int pageNo, int pageSize, String sortBy, String address, String... search) {
        // firstName:T, lastName:T

        List<SearchCriteria> criteriaList = new ArrayList<>();
        // Lay ds Users
        if (search != null) {
            String SEARCH_OPERATOR = "(\\w+?)([:<>])(.*)";
            Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
            for (String s : search) {
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    criteriaList.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            }
        }

        // Lay ds ban ghi
        List<User> users = getUsers(pageNo, pageSize, criteriaList, sortBy, address);

        Long totalElements = getTotalElements(criteriaList, address);

        return PageResponse.<User>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage((int) Math.ceil((double) totalElements / pageSize))
                .totalElement(totalElements)
                .data(users)
                .build();
    }

    private List<User> getUsers(int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        // Xu ly cac dieu kien tim kiem
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer queryConsumer = new UserSearchQueryCriteriaConsumer(criteriaBuilder, predicate, root);

        // join
        if (StringUtils.hasLength(address)) {
            Join<Address, User> join = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(join.get("building"), String.format("%%%s%%", address));
            criteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.where(predicate);
        }

        // sort
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(asc|desc)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String fieldName = matcher.group(1);
                String direction = matcher.group(3);
                if (direction.equalsIgnoreCase("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(fieldName)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(fieldName)));
                }
            }
        }

        return entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
    }

    private Long getTotalElements(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        // Xu ly cac dieu kien tim kiem
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchQueryCriteriaConsumer queryConsumer = new UserSearchQueryCriteriaConsumer(criteriaBuilder, predicate, root);

        // join
        if (StringUtils.hasLength(address)) {
            Join<Address, User> join = root.join("addresses");
            Predicate addressPredicate = criteriaBuilder.like(join.get("building"), String.format("%%%s%%", address));
            criteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.select(criteriaBuilder.countDistinct(root.get("id")));
            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(queryConsumer);
            predicate = queryConsumer.getPredicate();
            query.select(criteriaBuilder.countDistinct(root.get("id")));
            query.where(predicate);
        }

        return entityManager.createQuery(query).getSingleResult();
    }
}
