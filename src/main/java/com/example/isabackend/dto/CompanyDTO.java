package com.example.isabackend.dto;

import com.example.isabackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
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
    private Time startTime;
    private Time endTime;
    private Float averageGrade;
    private Integer appointmentId;
    private Set<User> admins;
}
