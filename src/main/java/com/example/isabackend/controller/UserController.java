package com.example.isabackend.controller;

import com.example.isabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{userId}/make-admin")
    public ResponseEntity<String> makeUserAdmin(@PathVariable Integer userId) {
        try {
            userService.makeUserAdmin(userId);
            return ResponseEntity.ok("User has been successfully assigned the ADMIN role.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userId);
        }
    }

    @PutMapping("/{userId}/make-company-admin")
    public ResponseEntity<String> makeUserCompanyAdmin(@PathVariable Integer userId) {
        try {
            userService.makeUserCompanyAdmin(userId);
            return ResponseEntity.ok("User has been successfully assigned the COMPANY ADMIN role.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found with ID: " + userId);
        }
    }
}

