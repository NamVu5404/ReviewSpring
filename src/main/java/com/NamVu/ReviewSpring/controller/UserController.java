package com.NamVu.ReviewSpring.controller;

import com.NamVu.ReviewSpring.dto.request.UserRequest;
import com.NamVu.ReviewSpring.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping(value = "/", headers = "apiKey=v1.0")
    public String addUser(@Valid @RequestBody UserRequest request) {
        return "User added";
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable int userId, @RequestBody UserRequest request) {
        System.out.println("Request update userId: " + userId);
        return "User updated";
    }

    @PatchMapping("/{userId}")
    public String changeStatus(@PathVariable int userId, @RequestParam(required = false) boolean status) {
        System.out.println("Request change user status, userId=" + userId);
        return "User status changed";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@Min(5) @PathVariable int userId) {
        System.out.println("Request delete userId=" + userId);
        return "User deleted";
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable int userId) {
        System.out.println("Request get user detail by userId=" + userId);
        return new UserResponse("firstName", "lastName", "phone", "email");
    }

    @GetMapping("/all")
    public List<UserResponse> getAll(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        System.out.println("Get all users");
        return List.of(new UserResponse("A", "B", "C", "D"),
                new UserResponse("e", "f", "g", "h"));
    }
}
