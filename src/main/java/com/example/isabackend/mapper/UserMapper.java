package com.example.isabackend.mapper;

import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO UsertoUserDTO(User user) {
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
        return userDTO;
    }

    public User UserDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setId(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setCountry(userDTO.getCountry());
        user.setCity(userDTO.getCity());
        user.setProfession(userDTO.getProfession());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setCompanyInfo(userDTO.getCompanyInfo());
        return user;
    }
}
