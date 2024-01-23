package com.example.isabackend.repository;

import com.example.isabackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByCompanyIdAndIsReservedFalse(Integer companyId);
}
