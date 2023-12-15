package com.example.isabackend.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "_appointment")
public class Appointment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Date date;

    @Column
    private Time time;

    @Column
    private Integer duration;

    @Column
    private Boolean isCompaniesAppointment;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
