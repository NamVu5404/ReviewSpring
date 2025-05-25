package com.NamVu.ReviewSpring.service;

import com.NamVu.ReviewSpring.dto.request.UserCreateRequest;
import com.NamVu.ReviewSpring.dto.request.UserUpdateRequest;
import com.NamVu.ReviewSpring.dto.response.PageResponse;
import com.NamVu.ReviewSpring.dto.response.UserDetailResponse;
import com.NamVu.ReviewSpring.enums.UserStatus;

import java.util.Set;

public interface UserService {

    long saveUser(UserCreateRequest request);

    void updateUser(long userId, UserUpdateRequest request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(Set<Long> userIds);

    UserDetailResponse getUser(long userId);

    PageResponse<UserDetailResponse> getAllUser(int pageNo, int pageSize, String sortBy, String direction);
}
