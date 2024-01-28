package com.example.isabackend.repository;

import com.example.isabackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByCompanyIdAndIsReservedFalse(Integer companyId);
    List<Appointment> findAllByDateAndCompany_IdAndIsReservedIsTrue(Date date, Integer companyId);
}
