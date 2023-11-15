package com.example.isabackend.service;

import com.example.isabackend.model.User;
import com.example.isabackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User exam) {
        return userRepository.save(exam);
    }

    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

}
