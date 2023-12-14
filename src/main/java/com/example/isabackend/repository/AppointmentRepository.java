package com.example.isabackend.repository;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}
