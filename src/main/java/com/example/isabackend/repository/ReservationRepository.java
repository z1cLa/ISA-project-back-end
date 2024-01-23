package com.example.isabackend.repository;

import com.example.isabackend.model.Reservation;
import com.example.isabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.user = ?1")
    public List<Reservation> getUserReservations(Integer id);
}
