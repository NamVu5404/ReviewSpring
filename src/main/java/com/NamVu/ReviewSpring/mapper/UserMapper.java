package com.NamVu.ReviewSpring.mapper;

import com.NamVu.ReviewSpring.dto.request.UserCreateRequest;
import com.NamVu.ReviewSpring.dto.request.UserUpdateRequest;
import com.NamVu.ReviewSpring.dto.response.UserDetailResponse;
import com.NamVu.ReviewSpring.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true)
    User mapToEntity(UserCreateRequest request);

    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updatedUser(@MappingTarget User user, UserUpdateRequest request);

    UserDetailResponse mapToDetailResponse(User user);
}
