package com.example.isabackend.service;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment findById(Integer id) { return appointmentRepository.findById(id).orElseGet(null);}

    public List<Appointment> findAll() {return appointmentRepository.findAll();}

    public Appointment save(Appointment exam) {return appointmentRepository.save(exam);}

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findByCompanyIdAndIsCompaniesAppointmentTrue(Integer companyId) {
        return appointmentRepository.findByCompanyIdAndIsCompaniesAppointmentTrue(companyId);
    }



}
