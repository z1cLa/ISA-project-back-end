package com.example.isabackend.service;

import com.example.isabackend.model.Cancellation;
import com.example.isabackend.model.Reservation;
import com.example.isabackend.repository.CancellationRepository;
import com.example.isabackend.repository.ReservationRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class CancellationService {

    @Autowired
    private CancellationRepository cancellationRepository;

    public Cancellation save(Cancellation exam) throws IOException, WriterException, MessagingException {
        // First, save the reservation to the database to get the generated ID
        return cancellationRepository.save(exam);
    }

    public List<Cancellation> getUserCancellations(Integer id){
        return this.cancellationRepository.getUserCancellations(id);
    }
}
