package com.example.isabackend.controller;

import com.example.isabackend.model.Appointment;
import com.example.isabackend.model.Company;
import com.example.isabackend.model.Equipment;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.service.AppointmentService;
import com.example.isabackend.service.ReservationService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@CrossOrigin
public class ReservationController {
    private final ReservationService reservationService;
    private final AppointmentService appointmentService;

    @PostMapping("/save")
    public Reservation saveReservation(@RequestBody Reservation reservation) throws IOException, WriterException, MessagingException {
        Reservation reservationRet = reservationService.save(reservation);
        return reservationRet;
    }

    @GetMapping("/id/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.getReservationById(id);
    }


    @GetMapping("/user/{id}")
    public List<Reservation> getUserReservations(@PathVariable Integer id) {
        return reservationService.getUserReservations(id);
    }

    @GetMapping("/company/{id}")
    public List<Reservation> getCompanyReservations(@PathVariable Integer id) {
        return reservationService.getCompanyReservations(id);
    }

    @DeleteMapping("/cancel/{reservationId}/{points}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer reservationId,
                                               @PathVariable Integer points) {
        try {
            reservationService.cancelReservation(reservationId, points);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/in-progress")
    public List<Reservation> getInProgressReservations() {
        return reservationService.getInProgressReservations();
    }

    @PutMapping("/finish/{reservationId}")
    public ResponseEntity<String> finishReservation(@PathVariable Integer reservationId) {
        Optional<Reservation> updatedReservation = reservationService.finishReservation(reservationId);

        return updatedReservation.map(reservation -> {
            return ResponseEntity.ok("Reservation with ID " + reservationId + " has been finished.");
        }).orElseGet(() ->
                ResponseEntity.status(404).body("Reservation with ID " + reservationId + " not found.")
        );
    }

    @GetMapping("/equipment/{id}")
    public Set<Equipment> getInProgressReservations(@PathVariable Integer id) {
        return reservationService.getReservationEquipment(id);
    }



    private boolean isAppointmentDateValid(Date appointmentDate) {
        Date currentDate = new Date();
        return !appointmentDate.before(currentDate);
    }


    @PutMapping("/update-status")
    public ResponseEntity<String> updateReservationStatus() {
        reservationService.updateReservationStatus();
        return ResponseEntity.ok("Reservation status updated successfully");
    }
    
    @GetMapping("/finishedForUser/{userId}")
    public List<Reservation> getFinishedReservationsByUserId(@PathVariable Integer userId) {
        return reservationService.getFinishedReservationsByUserId(userId);
    }

    @GetMapping("/totalPriceForReservation/{reservationId}")
    public int getTotalPriceForReservation(@PathVariable Integer reservationId) {
        return reservationService.getPriceForReservation(reservationId);
    }

    @GetMapping("/userQrReservations/{id}")
    public List<Reservation> getUserReservationsForQRCodes(@PathVariable Integer id) {
        return reservationService.getUserReservationsForQRCodes(id);
    }

}

