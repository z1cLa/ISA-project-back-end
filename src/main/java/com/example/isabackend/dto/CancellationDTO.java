package com.example.isabackend.dto;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Equipment;
import com.example.isabackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancellationDTO {
    private Integer id;
    private Integer AppointmentId;
    private Integer UserId;
}
