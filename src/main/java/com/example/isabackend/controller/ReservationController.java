package com.example.isabackend.controller;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.service.AppointmentService;
import com.example.isabackend.service.ReservationService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    private final AppointmentService appointmentService;

    @PostMapping("/save")
    public Reservation saveReservation(@RequestBody Reservation reservation) throws IOException, WriterException, MessagingException {
        Appointment appointment = appointmentService.findById(reservation.getAppointment().getId());
        appointmentService.updateWhenReserved(appointment);
        return reservationService.save(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.getReservationById(id);
    }
}
