package com.example.isabackend.controller;

import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Role;
import com.example.isabackend.model.User;
import com.example.isabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    @Autowired
    private UserService userService;

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
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
        User user = userService.findOne(id);

        if (user != null) {
            // Map User entity to UserDTO if needed
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole());
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPassword(user.getPassword());
            userDTO.setCountry(user.getCountry());
            userDTO.setCity(user.getCity());
            userDTO.setProfession(user.getProfession());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setCompanyInfo(user.getCompanyInfo());

            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUserDTO) {
        User existingUser = userService.findOne(id);

        if (existingUser != null) {
            // Update existingUser with values from updatedUserDTO
            existingUser.setFirstName(updatedUserDTO.getFirstName());
            existingUser.setLastName(updatedUserDTO.getLastName());
            existingUser.setEmail(updatedUserDTO.getEmail());
            existingUser.setPassword(updatedUserDTO.getPassword());
            existingUser.setCountry(updatedUserDTO.getCountry());
            existingUser.setCity(updatedUserDTO.getCity());
            existingUser.setProfession(updatedUserDTO.getProfession());
            existingUser.setPhoneNumber(updatedUserDTO.getPhoneNumber());
            existingUser.setCompanyInfo(updatedUserDTO.getCompanyInfo());

            // Save the updated user
            existingUser = userService.save(existingUser);

            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
