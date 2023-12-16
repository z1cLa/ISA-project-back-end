package com.example.isabackend.dto;

import com.example.isabackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Integer id;
    private String companyName;
    private String address;
    private String description;
    private Float averageGrade;
    private Set<User> admins;
}
