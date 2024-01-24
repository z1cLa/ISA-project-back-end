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

    @Autowired
    private UserService userService;

    public Appointment findById(Integer id) { return appointmentRepository.findById(id).orElseGet(null);}

    public List<Appointment> findAll() {return appointmentRepository.findAll();}

    public Appointment save(Appointment exam) {return appointmentRepository.save(exam);}

    public void remove(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findByCompanyIdAndIsReservedFalse(Integer companyId) {
        return appointmentRepository.findByCompanyIdAndIsReservedFalse(companyId);
    }

    public Appointment updateWhenReserved(Appointment appointment) {
        if (!appointmentRepository.existsById(appointment.getId())) {
            return null;
        }
        appointment.setIsReserved(true);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return updatedAppointment;
    }

    public Appointment updateWhenCanceled(Appointment appointment, Integer points){
        if (!appointmentRepository.existsById(appointment.getId())) {
            return null;
        }
        appointment.setIsReserved(false);
        this.userService.addPenaltyPoints(appointment.getUser().getId().intValue(), points);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return updatedAppointment;
    }



}
