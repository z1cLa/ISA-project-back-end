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
@Table(name = "_company")
public class Company {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String companyName;

    @Column
    private String address;

    @Column
    private String description;

    @Column
    private Float averageGrade;

    @Column
    private Integer appointmentId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointmentId")
    private List<Appointment> freeAppointments;

    @Column
    private Integer adminId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminId")
    private List<User> otherAdmins;


}
