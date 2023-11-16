package com.example.isabackend.dto;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDTO {
    private Integer id;
    private String equipmentName;
    private String equipmentType;
    private String equipmentDescription;
    private Integer equipmentPrice;
    private Integer companyId;
}
