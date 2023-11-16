package com.example.isabackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_equipment")
public class Equipment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String equipmentName;

    @Column
    private String equipmentType;

    @Column
    private String equipmentDescription;

    @Column
    private Integer equipmentPrice;

    @Column
    private Integer companyId;
}
