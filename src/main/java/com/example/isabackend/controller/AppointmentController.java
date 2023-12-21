package com.example.isabackend.controller;

import com.example.isabackend.dto.AppointmentDTO;
import com.example.isabackend.dto.UserDTO;
import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.User;
import com.example.isabackend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/byCompany/{companyId}")
    public List<Appointment> getAppointmentsByCompanyIdAndIsCompaniesAppointmentTrue(@PathVariable Integer companyId) {
        return appointmentService.findByCompanyIdAndIsCompaniesAppointmentTrue(companyId);
    }

    @PutMapping(value = "/update/{id}", consumes = "application/json")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO updatedAppointmentDTO) {
        Appointment existingAppointment = appointmentService.findById(id);

        if (existingAppointment != null) {
            existingAppointment = appointmentService.updateWhenReserved(existingAppointment);
            return new ResponseEntity<>(updatedAppointmentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
