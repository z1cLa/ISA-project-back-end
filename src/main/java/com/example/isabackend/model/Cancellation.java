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
@Table(name = "_cancellation")
public class Cancellation {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Integer AppointmentId;

    @Column
    private Integer UserId;
}
