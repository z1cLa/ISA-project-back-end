package com.example.isabackend.controller;

import com.example.isabackend.dto.JwtAuthenticationRequest;
import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.dto.UserTokenState;
import com.example.isabackend.exception.ResourceConflictException;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.Role;
import com.example.isabackend.model.User;
import com.example.isabackend.service.UserService;
import com.example.isabackend.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getEmail());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }


    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @PostMapping(value = "register")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userRequest, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findByEmail(userRequest.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        User user = this.userService.save(userRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
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

    @GetMapping("/whoami")
    public User user(Principal user) {
        return this.userService.findByEmail(user.getName());
    }

    @GetMapping("/oneUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id)
    {
        return new ResponseEntity<>(this.userService.findOne(id), HttpStatus.OK);
    }


//    @GetMapping(value = "/user/{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
//        User user = userService.findOne(id);
//
//        if (user != null) {
//            // Map User entity to UserDTO if needed
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail(user.getEmail());
//            userDTO.setRole(user.getRole());
//            userDTO.setId(user.getId());
//            userDTO.setFirstName(user.getFirstName());
//            userDTO.setLastName(user.getLastName());
//            userDTO.setPassword(user.getPassword());
//            userDTO.setCountry(user.getCountry());
//            userDTO.setCity(user.getCity());
//            userDTO.setProfession(user.getProfession());
//            userDTO.setPhoneNumber(user.getPhoneNumber());
//            userDTO.setCompanyInfo(user.getCompanyInfo());
//
//            return new ResponseEntity<>(userDTO, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @PutMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO updatedUserDTO) {
        User existingUser = userService.findOne(id);

        if (existingUser != null) {
            // Update existingUser with values from updatedUserDTO
            existingUser.setFirstName(updatedUserDTO.getFirstName());
            existingUser.setLastName(updatedUserDTO.getLastName());
            existingUser.setEmail(updatedUserDTO.getEmail());
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
