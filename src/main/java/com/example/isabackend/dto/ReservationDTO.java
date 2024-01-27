package com.example.isabackend.dto;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Equipment;
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
public class ReservationDTO {
    private Integer id;
    private String status;
    private User user;
    private Appointment appointment;
    private Set<Equipment> equipments;
}
