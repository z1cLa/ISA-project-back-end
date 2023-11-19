package com.example.isabackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "_user_company",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"})
    )
    private Set<User> admins = new HashSet<>();
}
