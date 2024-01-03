package com.example.isabackend.service;

import com.example.isabackend.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation save(Reservation exam) {
        return reservationRepository.save(exam);
    }
}
