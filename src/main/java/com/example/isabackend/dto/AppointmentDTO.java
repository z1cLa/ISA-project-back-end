package com.example.isabackend.dto;

import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
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
    private Date date;
    private Time time;
    private Integer duration;
    private Boolean isCompaniesAppointment;
    private Boolean isReserved;
    private Company company;
    private User user;
}