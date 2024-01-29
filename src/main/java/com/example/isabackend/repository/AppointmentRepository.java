package com.example.isabackend.repository;

import com.example.isabackend.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByCompanyIdAndIsCompaniesAppointmentIsTrueAndIsReservedFalse(Integer companyId);
    List<Appointment> findAllByDateAndCompany_IdAndIsReservedIsTrue(Date date, Integer companyId);
    List<Appointment> findAllByDateAndCompany_IdAndIsCompaniesAppointmentIsTrue(Date date, Integer companyId);
}
