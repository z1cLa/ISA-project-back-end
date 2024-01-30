package com.example.isabackend.repository;

import com.example.isabackend.model.Equipment;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1")
    public List<Reservation> getUserReservations(Integer id);

    @Query("SELECT r FROM Reservation r WHERE r.appointment.company.id = ?1")
    public List<Reservation> getCompanyReservations(Integer id);

    List<Reservation> findByStatus(String status);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = ?1 AND r.status = 'In progress'")
    public List<Reservation> getUserInProgressReservations(Integer id);
    List<Reservation> findByUserIdAndStatus(Integer userId, String status);

    @Query("SELECT r.equipments FROM Reservation r WHERE r.id = :reservationId")
    Set<Equipment> findEquipmentsByReservationId(@Param("reservationId") Integer reservationId);

    List<Reservation> findByUserId(Integer userId);
}
