package com.admin_service.demo.controller;

import com.admin_service.demo.Service.UserService;
import com.admin_service.demo.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public User updateUserStatus(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return userService.updateUserStatus(userId, status);
    }

}
