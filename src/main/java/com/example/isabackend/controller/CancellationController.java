package com.example.isabackend.controller;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Cancellation;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.service.AppointmentService;
import com.example.isabackend.service.CancellationService;
import com.example.isabackend.service.ReservationService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cancellation")
@RequiredArgsConstructor
public class CancellationController {
    private final CancellationService cancellationService;

    @PostMapping("/save")
    public Cancellation saveCancellation(@RequestBody Cancellation cancellation) throws IOException, WriterException, MessagingException {
        return cancellationService.save(cancellation);
    }

    @GetMapping("/user/{id}")
    public List<Cancellation> getUserCancellations(@PathVariable Integer id) {
        return cancellationService.getUserCancellations(id);
    }


}
