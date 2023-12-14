package com.example.isabackend.controller;

import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.Role;
import com.example.isabackend.model.User;
import com.example.isabackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
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

        if(user.getFirstName().isBlank() || user.getLastName().isBlank() || user.getEmail().isBlank() || user.getPassword().length() < 8
        || user.getCountry().isBlank() || user.getCity().isBlank() || user.getProfession().isBlank() || user.getPhoneNumber().length() < 7
        || user.getCompanyInfo().isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        byte[] randomBytes = new byte[48]; // 48 bytes will result in a 64-character Base64-encoded string
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        String randomString = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        user.setVerificationCode(randomString);
        user.setVerified(false);

        userService.sendRegistrationMail(user);

        user = userService.save(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam("code") String code) {
        // Call the UserService to verify the email
        boolean success = userService.verify(code);

        if (success) {
            return new ResponseEntity<>("Email verified successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid verification code.", HttpStatus.BAD_REQUEST);
        }
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
            existingUser = userService.update(existingUser,updatedUserDTO);
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
