package com.example.isabackend.config;

import com.example.isabackend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    private ReservationService reservationService;

    @Scheduled(fixedRate = 60000)
    public void updateReservationStatus() {
        reservationService.updateReservationStatus();
    }
}
