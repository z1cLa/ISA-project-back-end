package com.example.isabackend.controller;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    private final AppointmentService appointmentService;

    @PostMapping("/save")
    public Reservation saveReservation(@RequestBody Reservation reservation) {
        Appointment appointment = appointmentService.findById(reservation.getAppointment().getId());
        appointmentService.updateWhenReserved(appointment);
        return reservationService.save(reservation);
    }
}
