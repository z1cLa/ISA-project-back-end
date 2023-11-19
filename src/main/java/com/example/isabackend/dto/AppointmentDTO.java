package com.example.isabackend.dto;

import com.example.isabackend.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Integer id;
    private String adminName;
    private String adminSurname;
    private Date date;
    private Time time;
    private Integer duration;
    private Company company;
}
