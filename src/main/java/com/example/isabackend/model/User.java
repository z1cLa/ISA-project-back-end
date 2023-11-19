package com.example.isabackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phoneNumber;
    @Column
    private String country;
    @Column
    private String city;
    @Column
    private String profession;
    @Column
    private String companyInfo;
    @Column
    private Role role;

    @Column(name = "verification_code", length = 64, updatable = false)
    private String verificationCode;

    @Column
    private boolean isVerified;
}
