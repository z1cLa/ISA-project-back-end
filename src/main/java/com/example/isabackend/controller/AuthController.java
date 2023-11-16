package com.example.isabackend.controller;

import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.Role;
import com.example.isabackend.model.User;
import com.example.isabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<UserDTO> saveCourse(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setProfession(userDTO.getProfession());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCompanyInfo(userDTO.getCompanyInfo());
        user.setRole(Role.USER);

        user = userService.save(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
