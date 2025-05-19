package com.NamVu.ReviewSpring.controller;

import com.NamVu.ReviewSpring.configuration.Translator;
import com.NamVu.ReviewSpring.dto.request.UserRequest;
import com.NamVu.ReviewSpring.dto.response.ResponseData;
import com.NamVu.ReviewSpring.dto.response.ResponseError;
import com.NamVu.ReviewSpring.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Operation(summary = "summary", description = "description")
    @PostMapping(value = "/")
    public ResponseData<Integer> addUser(@Valid @RequestBody UserRequest request) {
//        return new ResponseData<>(HttpStatus.CREATED.value(), "User added successfully", 1);

        return ResponseData.<Integer>builder()
                .status(HttpStatus.CREATED.value())
                .message(Translator.toLocale("user.add.success"))
                .data(1)
                .build();
    }

    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable int userId, @RequestBody UserRequest request) {
        System.out.println("Request update userId: " + userId);
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
        return ResponseData.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(Translator.toLocale("user.upd.success"))
                .build();
    }

    @PatchMapping("/{userId}")
    public ResponseData<?> changeStatus(@PathVariable int userId, @RequestParam(required = false) boolean status) {
        System.out.println("Request change user status, userId=" + userId);
//        return new ResponseError(HttpStatus.NOT_FOUND.value(), "User not found");
//        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User change status successfully");
        return ResponseError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("User not found")
                .build();
    }

    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@Min(5) @PathVariable int userId) {
        System.out.println("Request delete userId=" + userId);
//        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully");
        return ResponseData.builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("User deleted successfully")
                .build();
    }

    @GetMapping("/{userId}")
    public ResponseData<UserResponse> getUser(@PathVariable int userId) {
        System.out.println("Request get user detail by userId=" + userId);
//        return new ResponseData<>(HttpStatus.OK.value(), "user",
//                new UserResponse("firstName", "lastName", "phone", "email", LocalDate.now()));

        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("User")
                .data(UserResponse.builder()
                        .firstName("Nam")
                        .lastName("Vu")
                        .phone("0987654321")
                        .email("nam@email.com")
                        .dateOfBirth(LocalDate.parse("2004-04-05"))
                        .build())
                .build();
    }

    @GetMapping("/all")
    public ResponseData<List<UserResponse>> getAll(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        System.out.println("Get all users");
//        return new ResponseData<>(HttpStatus.OK.value(), "list user",
//                List.of(new UserResponse("A", "B", "C", "D", LocalDate.now()),
//                        new UserResponse("e", "f", "g", "h", LocalDate.now())));

        return ResponseData.<List<UserResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("List users")
                .data(List.of(
                        UserResponse.builder()
                                .firstName("Nam")
                                .lastName("Vu")
                                .phone("0987654321")
                                .email("nam@email.com")
                                .dateOfBirth(LocalDate.parse("2004-04-05"))
                                .build(),
                        UserResponse.builder()
                                .firstName("A")
                                .lastName("B")
                                .phone("0123456789")
                                .email("ab@email.com")
                                .dateOfBirth(LocalDate.parse("2004-04-05"))
                                .build()))
                .build();
    }
}
