package com.NamVu.ReviewSpring.service;

import com.NamVu.ReviewSpring.dto.request.UserCreateRequest;
import com.NamVu.ReviewSpring.dto.request.UserUpdateRequest;
import com.NamVu.ReviewSpring.dto.response.PageResponse;
import com.NamVu.ReviewSpring.dto.response.UserDetailResponse;
import com.NamVu.ReviewSpring.enums.UserStatus;
import com.NamVu.ReviewSpring.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {

    long saveUser(UserCreateRequest request);

    void updateUser(long userId, UserUpdateRequest request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(Set<Long> userIds);

    UserDetailResponse getUser(long userId);

    PageResponse<UserDetailResponse> getAllUser(int pageNo, int pageSize, String sortBy, String direction);

    PageResponse<?> searchWithSqlQuery(String search, int pageNo, int pageSize, String sortBy, String direction);

    PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search);

    PageResponse<User> advanceSearchWithSpecification(Pageable pageable, String[] user, String[] address);
}
