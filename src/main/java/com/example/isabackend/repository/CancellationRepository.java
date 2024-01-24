package com.example.isabackend.repository;

import com.example.isabackend.model.Cancellation;
import com.example.isabackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CancellationRepository extends JpaRepository<Cancellation, Integer> {

    @Query("SELECT c FROM Cancellation c WHERE c.UserId = ?1")
    public List<Cancellation> getUserCancellations(Integer id);
}
