package com.example.isabackend.controller;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.User;
import com.example.isabackend.service.AppointmentService;
import com.example.isabackend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
@CrossOrigin
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Integer id) {
        return appointmentService.findById(id);
    }

    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.findAll();
    }
    @PostMapping("/save")
    public Appointment saveAppointment(@RequestBody Appointment appointment) {
        return appointmentService.save(appointment);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAppointment(@PathVariable Integer id) {
        appointmentService.remove(id);
    }

}
