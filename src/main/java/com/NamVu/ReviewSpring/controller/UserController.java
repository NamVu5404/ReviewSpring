package com.NamVu.ReviewSpring.controller;

import com.NamVu.ReviewSpring.configuration.Translator;
import com.NamVu.ReviewSpring.dto.request.UserCreateRequest;
import com.NamVu.ReviewSpring.dto.request.UserUpdateRequest;
import com.NamVu.ReviewSpring.dto.response.PageResponse;
import com.NamVu.ReviewSpring.dto.response.ResponseData;
import com.NamVu.ReviewSpring.dto.response.UserDetailResponse;
import com.NamVu.ReviewSpring.enums.UserStatus;
import com.NamVu.ReviewSpring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Add new user", description = "Send a request via this API to create new user")
    @PostMapping(value = "/")
    public ResponseData<Long> addUser(@Valid @RequestBody UserCreateRequest request) {
        return ResponseData.<Long>builder()
                .status(HttpStatus.CREATED.value())
                .message(Translator.toLocale("user.add.success"))
                .data(userService.saveUser(request))
                .build();
    }

    @Operation(summary = "Update user", description = "Send a request via this API to update user")
    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable int userId, @RequestBody UserUpdateRequest request) {
        userService.updateUser(userId, request);

        return ResponseData.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(Translator.toLocale("user.upd.success"))
                .build();
    }

    @Operation(summary = "Change status of user", description = "Send a request via this API to change status of user")
    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@PathVariable int userId, @RequestParam(required = false) UserStatus status) {
        userService.changeStatus(userId, status);

        return ResponseData.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(Translator.toLocale("user.change-status.success"))
                .build();
    }

    @Operation(summary = "Delete user permanently", description = "Send a request via this API to delete user permanently")
    @DeleteMapping("/{userIds}")
    public ResponseData<?> deleteUser(@PathVariable Set<Long> userIds) {
        userService.deleteUser(userIds);

        return ResponseData.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message(Translator.toLocale("user.del.success"))
                .build();
    }

    @Operation(summary = "Get user detail", description = "Send a request via this API to get user information")
    @GetMapping("/{userId}")
    public ResponseData<UserDetailResponse> getUser(@PathVariable int userId) {
        return ResponseData.<UserDetailResponse>builder()
                .status(HttpStatus.OK.value())
                .message(Translator.toLocale("user.get-detail.success"))
                .data(userService.getUser(userId))
                .build();
    }

    @Operation(summary = "Get list of users per pageNo", description = "Send a request via this API to get user list by pageNo and pageSize")
    @GetMapping("/list")
    public ResponseData<PageResponse<UserDetailResponse>> getAll(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        return ResponseData.<PageResponse<UserDetailResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("List users")
                .data(userService.getAllUser(pageNo, pageSize, sortBy, direction))
                .build();
    }
}
