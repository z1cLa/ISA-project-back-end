package com.example.isabackend.repository;

import com.example.isabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("UPDATE User u SET u.isVerified = true WHERE u.id = ?1")
    @Modifying
    public void verifyUser(Integer id);
    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    public User findByVerificationCode(String code);
}
